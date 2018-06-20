package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.PartnersNotificationEntity;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PartnersNotificationRepository extends AbstractRepository<Long, PartnersNotificationEntity> {

    public List<PartnersNotificationEntity> getAllOfCompany(Long companyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PartnersNotificationEntity> q = cb.createQuery(PartnersNotificationEntity.class);
        Root<PartnersNotificationEntity> c = q.from(PartnersNotificationEntity.class);

        Predicate predIsReaded = cb.equal(c.get("isReaded"), false);
        Predicate predReceiverId = cb.equal(c.get("receiverId"), companyId);
        q.where(cb.and(predIsReaded, predReceiverId));

        TypedQuery<PartnersNotificationEntity> query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
