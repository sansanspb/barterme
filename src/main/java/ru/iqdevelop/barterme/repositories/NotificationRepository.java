package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.NotificationEntity;
import ru.iqdevelop.barterme.models.enums.NotificationStatusEnum;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class NotificationRepository extends AbstractRepository<Long, NotificationEntity> {

    public List<NotificationEntity> getWaited() {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<NotificationEntity> q = cb.createQuery(NotificationEntity.class);
            Root<NotificationEntity> c = q.from(NotificationEntity.class);
            Predicate pred = cb.equal(c.get("status"), NotificationStatusEnum.WAIT);
            q.where(pred);
            TypedQuery<NotificationEntity> query = entityManager.createQuery(q);
            return query.getResultList();
    }
}
