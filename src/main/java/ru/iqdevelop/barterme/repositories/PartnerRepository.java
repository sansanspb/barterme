package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.PartnerEntity;
import ru.iqdevelop.barterme.models.enums.PartnerStatusEnum;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PartnerRepository extends AbstractRepository<Long, PartnerEntity> {

    public PartnerEntity getCurrentPartnerByCompanies(Long senderCompanyId, Long receiverCompanyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PartnerEntity> q = cb.createQuery(PartnerEntity.class);
        Root<PartnerEntity> c = q.from(PartnerEntity.class);

        Predicate predSenderId = cb.equal(c.get("fkSenderId"), senderCompanyId);
        Predicate predReceiverId = cb.equal(c.get("fkReceiverId"), receiverCompanyId);
        Predicate predActiveStatus = cb.equal(c.get("status"), PartnerStatusEnum.ACTIVE);
        Predicate predWaitStatus = cb.equal(c.get("status"), PartnerStatusEnum.WAIT);
        q.where(cb.and(cb.and(predSenderId, predReceiverId), cb.or(predWaitStatus, predActiveStatus)));

        TypedQuery<PartnerEntity> query = entityManager.createQuery(q);

        List<PartnerEntity> partners = query.getResultList();
        if (partners == null || partners.isEmpty()) {
            return null;
        } else {
            return partners.get(0);
        }
    }

    public PartnerEntity getPartnerInviteFromCompany(Long fromId, Long toId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PartnerEntity> q = cb.createQuery(PartnerEntity.class);
        Root<PartnerEntity> c = q.from(PartnerEntity.class);

        Predicate predSenderId = cb.equal(c.get("fkSenderId"), fromId);
        Predicate predReceiverId = cb.equal(c.get("fkReceiverId"), toId);
        Predicate predWaitStatus = cb.equal(c.get("status"), PartnerStatusEnum.WAIT);
        q.where(cb.and(cb.and(predSenderId, predReceiverId, predWaitStatus)));

        TypedQuery<PartnerEntity> query = entityManager.createQuery(q);

        List<PartnerEntity> partners = query.getResultList();
        if (partners == null || partners.isEmpty()) {
            return null;
        } else {
            return partners.get(0);
        }
    }

    public PartnerEntity getActivePartnerOfCompanies(Long oneCompanyId, Long twoCompanyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PartnerEntity> q = cb.createQuery(PartnerEntity.class);
        Root<PartnerEntity> c = q.from(PartnerEntity.class);

        Predicate predSenderId1 = cb.equal(c.get("fkSenderId"), oneCompanyId);
        Predicate predReceiverId1 = cb.equal(c.get("fkReceiverId"), twoCompanyId);
        Predicate oneToTwo = cb.and(predSenderId1, predReceiverId1);

        Predicate predSenderId2 = cb.equal(c.get("fkSenderId"), twoCompanyId);
        Predicate predReceiverId2 = cb.equal(c.get("fkReceiverId"), oneCompanyId);
        Predicate twoToOne = cb.and(predSenderId2, predReceiverId2);

        Predicate predOr = cb.or(twoToOne, oneToTwo);

        Predicate predActiveStatus = cb.equal(c.get("status"), PartnerStatusEnum.ACTIVE);

        q.where(cb.and(predOr, predActiveStatus));

        TypedQuery<PartnerEntity> query = entityManager.createQuery(q);

        List<PartnerEntity> partners = query.getResultList();
        if (partners == null || partners.isEmpty()) {
            return null;
        } else {
            return partners.get(0);
        }
    }

    public List<PartnerEntity> getAllPartnersOfCompany(Long companyId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PartnerEntity> q = cb.createQuery(PartnerEntity.class);
        Root<PartnerEntity> c = q.from(PartnerEntity.class);

        Predicate predSenderId = cb.equal(c.get("fkSenderId"), companyId);
        Predicate predReceiverId = cb.equal(c.get("fkReceiverId"), companyId);
        q.where(cb.or(predSenderId, predReceiverId));

        TypedQuery<PartnerEntity> query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
