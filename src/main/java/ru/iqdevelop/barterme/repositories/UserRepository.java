package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.UserEntity;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository
public class UserRepository extends AbstractRepository<Long, UserEntity> {

	public UserEntity findByEmail(String email) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserEntity> q = cb.createQuery(UserEntity.class);
			Root<UserEntity> c = q.from(UserEntity.class);
			
			ParameterExpression<String> emailParam = cb.parameter(String.class);
			Predicate pred = cb.equal(c.get("email"), emailParam);
			q.where(pred);
	
			TypedQuery<UserEntity> query = entityManager.createQuery(q);
			query.setParameter(emailParam, email);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
