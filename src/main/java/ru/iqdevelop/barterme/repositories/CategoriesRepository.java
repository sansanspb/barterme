package ru.iqdevelop.barterme.repositories;

import org.springframework.stereotype.Repository;
import ru.iqdevelop.barterme.entities.CategoryEntity;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CategoriesRepository extends AbstractRepository<Long, CategoryEntity> {

    public List<CategoryEntity> getChilds(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> q = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> c = q.from(CategoryEntity.class);

        Predicate pred = cb.equal(c.get("parent"), id);
        q.where(pred);

        TypedQuery<CategoryEntity> query = entityManager.createQuery(q);

        return query.getResultList();
    }

    public List<CategoryEntity> getParents() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> q = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> c = q.from(CategoryEntity.class);

        Predicate pred = cb.isNull(c.get("parent"));
        q.where(pred);

        TypedQuery<CategoryEntity> query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
