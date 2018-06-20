package ru.iqdevelop.barterme.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.CategoryEntity;
import ru.iqdevelop.barterme.repositories.CategoriesRepository;

import java.util.List;

@Service
@EnableTransactionManagement
public class CategoriesService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Transactional(readOnly = true)
    public List<CategoryEntity> getAll() {
        return categoriesRepository.getAll();
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> getSubcategs(Long id) {
        return categoriesRepository.getChilds(id);
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> getParents() {
        return categoriesRepository.getParents();
    }

    @Transactional(readOnly = true)
    public CategoryEntity getById(Long id) {
        return categoriesRepository.getById(id);
    }
}
