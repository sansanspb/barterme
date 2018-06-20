package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.CategoryEntity;
import ru.iqdevelop.barterme.entities.CompanyEntity;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class CompanyRepository extends AbstractRepository<Long, CompanyEntity> {

    public List<CompanyEntity> getAllOthers(Long companyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CompanyEntity> q = cb.createQuery(CompanyEntity.class);
        Root<CompanyEntity> c = q.from(CompanyEntity.class);

        Predicate predCompanyId = cb.notEqual(c.get("companyId"), companyId);
        q.where(predCompanyId);

        TypedQuery<CompanyEntity> query = entityManager.createQuery(q);

        return query.getResultList();
    }

    public List<CompanyEntity> getCompaniesBySubCategory(Long companyId, Long categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CompanyEntity> q = cb.createQuery(CompanyEntity.class);
        Root<CompanyEntity> c = q.from(CompanyEntity.class);
        Join<CompanyEntity, CategoryEntity> joinCategs = c.join("offerCategories", JoinType.LEFT);

        Predicate predCompanyId = cb.notEqual(c.get("companyId"), companyId);
        Predicate predCategoryId = cb.equal(joinCategs.get("categoryId"), categoryId);
        q.where(cb.and(predCompanyId, predCategoryId));

        TypedQuery<CompanyEntity> query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
