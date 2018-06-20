package ru.iqdevelop.barterme.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractRepository<PK extends Serializable, T> {
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public AbstractRepository() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@PersistenceContext
	EntityManager entityManager;
	
	protected EntityManager getEntityManager(){
		return this.entityManager;
	}
	
	public List<T> getAll() {
		String query = String.format("SELECT p FROM %s p", persistentClass.getName());
		@SuppressWarnings("unchecked")
		List<T> result = entityManager.createQuery(query).getResultList();
		return result;
	}

	public T getById(PK key) {
		if (key == null) {
			return null;
		}
		return (T) entityManager.find(persistentClass, key);
	}

	public void insert(T entity) {
		entityManager.persist(entity);
	}
	
	public void update(T entity) {
		entityManager.merge(entity);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}
	
	public void delete(PK key) {
		T entity = getById(key);
		entityManager.remove(entity);
	}
}
