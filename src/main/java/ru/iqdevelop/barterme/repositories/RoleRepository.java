package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.RoleEntity;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository
public class RoleRepository extends AbstractRepository<Long, RoleEntity> {

    public RoleEntity getByTitle(String title) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<RoleEntity> q = cb.createQuery(RoleEntity.class);
            Root<RoleEntity> c = q.from(RoleEntity.class);
            ParameterExpression<String> param = cb.parameter(String.class);
            Predicate pred = (title != null) ? cb.equal(c.get("title"), param) : cb.isNull(c.get("title"));
            q.where(pred);

            TypedQuery<RoleEntity> query = entityManager.createQuery(q);
            if (title != null)
                query.setParameter(param, title);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
