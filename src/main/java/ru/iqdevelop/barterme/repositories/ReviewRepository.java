package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.ReviewEntity;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ReviewRepository extends AbstractRepository<Long, ReviewEntity> {

    public List<ReviewEntity> getCompanyReviews(Long companyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReviewEntity> q = cb.createQuery(ReviewEntity.class);
        Root<ReviewEntity> c = q.from(ReviewEntity.class);

        Predicate predCompanyId = cb.equal(c.get("fkCompanyId"), companyId);
        q.where(predCompanyId);

        TypedQuery<ReviewEntity> query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
