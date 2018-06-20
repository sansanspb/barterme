package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.CompanyEntity;
import ru.iqdevelop.barterme.entities.ReviewEntity;
import ru.iqdevelop.barterme.entities.UserEntity;
import ru.iqdevelop.barterme.models.SendReviewRequestModel;
import ru.iqdevelop.barterme.repositories.CompanyRepository;
import ru.iqdevelop.barterme.repositories.ReviewRepository;
import ru.iqdevelop.barterme.repositories.UserRepository;

import java.util.List;

@Service
@EnableTransactionManagement
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<ReviewEntity> getCompanyReviews(Long companyId) {
        return reviewRepository.getCompanyReviews(companyId);
    }

    @Transactional
    public void createReview(String userEmail, SendReviewRequestModel model) {
        UserEntity user = userRepository.findByEmail(userEmail);

        if (user == null) {
            throw new RuntimeException("Такого пользователя нет или вы не зарегистрированны!");
        }

        CompanyEntity currCompany = companyRepository.getById(user.getUserId());
        if (currCompany == null) {
            throw new RuntimeException("Сначало нужно зарегистрироваться!");
        }

        ReviewEntity newReview = new ReviewEntity();
        newReview.setAuthor(currCompany);
        newReview.setContactPerson(currCompany.getContactPerson());
        newReview.setCompany(companyRepository.getById(model.getCompanyId()));
        newReview.setGood(model.getGood());
        newReview.setMessage(model.getMessage());
        reviewRepository.insert(newReview);
    }
}
