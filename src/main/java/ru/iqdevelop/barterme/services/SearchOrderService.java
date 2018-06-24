package ru.iqdevelop.barterme.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.CompanyEntity;
import ru.iqdevelop.barterme.entities.SearchOrderEntity;
import ru.iqdevelop.barterme.entities.UserEntity;
import ru.iqdevelop.barterme.models.SearchOrderModel;
import ru.iqdevelop.barterme.repositories.CompanyRepository;
import ru.iqdevelop.barterme.repositories.PhotoRepository;
import ru.iqdevelop.barterme.repositories.SearchOrderRepository;
import ru.iqdevelop.barterme.repositories.UserRepository;
import ru.iqdevelop.barterme.utils.Helpers;

import java.util.List;

@Service
@EnableTransactionManagement
public class SearchOrderService {

    private static final Logger logger = LoggerFactory.getLogger(SearchOrderService.class);

    @Autowired
    private SearchOrderRepository searchOrderRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<SearchOrderEntity> getAll() {
        return searchOrderRepository.getAll();
    }

    @Transactional
    public void save(String userEmail, SearchOrderModel searchOrderModel) {
        SearchOrderEntity newEntity = new SearchOrderEntity();
        CompanyEntity currCompany = getCurrentCompanyOrNull(userEmail);
        newEntity.setCompany(currCompany);

        modelToEntity(searchOrderModel, newEntity);

        searchOrderRepository.insert(newEntity);
    }

    private void modelToEntity(SearchOrderModel searchOrderModel, SearchOrderEntity searchOrderEntity) {
        searchOrderEntity.setCategories(searchOrderModel.getCategories());
        searchOrderEntity.setContacts(searchOrderModel.getContacts());
        searchOrderEntity.setDeadline(Helpers.getCurrentTimestamp());
        searchOrderEntity.setMarketingTask(searchOrderModel.getMarketingTask());
        searchOrderEntity.setOffer(searchOrderModel.getOffer());
        searchOrderEntity.setSearch(searchOrderModel.getSearch());
        searchOrderEntity.setRegion(searchOrderModel.getRegion());

        searchOrderEntity.setPhoto(null);
    }

    private CompanyEntity getCurrentCompanyOrNull(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail);

        if (user == null) {
            return null;
        }

        CompanyEntity currCompany = companyRepository.getById(user.getUserId());
        if (currCompany == null) {
            currCompany = new CompanyEntity();
            currCompany.setCompanyId(user.getUserId());
            currCompany.setContactEmail(userEmail);
            currCompany.setCaption(user.getTitle());
            companyRepository.insert(currCompany);
        }
        return currCompany;
    }
}
