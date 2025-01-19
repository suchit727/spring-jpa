package com.practice.spring_boot_query_practice.repository;

import com.practice.spring_boot_query_practice.entity.DepartmentEntity;
import com.practice.spring_boot_query_practice.entity.EmployeeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CriteriaEmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<EmployeeEntity> getAllEmployee() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public List<EmployeeEntity> getAllEmployeeByName(String department) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        query.select(root).where(cb.equal(root.get("name"), department));
        return entityManager.createQuery(query).getResultList();
    }

    public List<EmployeeEntity> getAllEmployeesByName(String department, String minSalary) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = criteriaBuilder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);
        Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), minSalary);
        query.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("name"), department),
                        predicate
                )
        );
        return entityManager.createQuery(query).getResultList();
    }

    public List<EmployeeEntity> getAllEmployeeSortedBySalary() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        query.select(root).orderBy(cb.desc(root.get("salary")));
        return entityManager.createQuery(query).getResultList();
    }

    public List<EmployeeEntity> getAllEmployeeSortedByName() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> criteriaQuery = criteriaBuilder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = criteriaQuery.from(EmployeeEntity.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get("name")));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<EmployeeEntity> getAllEmployeeInPaginatedForm(Integer pageNumber, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = criteriaBuilder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        query.select(root);
        return entityManager.createQuery(query)
                .setFirstResult(pageNumber)
                .setMaxResults(size).getResultList();
    }

    public List<EmployeeEntity> getAllEmployeesByDepartmentName(String departmentName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        Join<EmployeeEntity, DepartmentEntity> join = root.join("department");
//        Join<EmployeeEntity, DepartmentEntity> join= root.join("department", JoinType.LEFT);
        query.select(root).where(cb.equal(join.get("name"), departmentName));
        return entityManager.createQuery(query).getResultList();
    }

    public Long getAllEmployeeByName() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        query.select(cb.countDistinct(root.get("name")));
        return entityManager.createQuery(query).getSingleResult();
    }

    public Integer getHighestPaidEmployee() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        query.select(cb.max(root.get("salary")));
        return entityManager.createQuery(query).getSingleResult();
    }


    public List<DepartmentEntity> getDepartmentStats(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DepartmentEntity> criteriaQuery = criteriaBuilder.createQuery(DepartmentEntity.class);
        Root<DepartmentEntity> root = criteriaQuery.from(DepartmentEntity.class);


        criteriaQuery.select(root)
                .groupBy(root.get("department"))
                .having(criteriaBuilder.equal(root.get("name"), name));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<EmployeeEntity> getEmployeeAboveAverageSalary() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = criteriaBuilder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        Subquery<Double> subquery = query.subquery(Double.class);
        Root<EmployeeEntity> rootSubQuery = subquery.from(EmployeeEntity.class);
        subquery.select(criteriaBuilder.avg(rootSubQuery.get("salary")));


        query.select(root)
                .where(criteriaBuilder.greaterThan(root.get("salary"), subquery));

        return entityManager.createQuery(query).getResultList();
    }

    public List<EmployeeEntity> getEmployeeWithDynamicQuery(String name, Integer salary) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = criteriaBuilder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(name)) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }

        if (salary != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), salary));
        }

        query.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));


        return entityManager.createQuery(query).getResultList();
    }

}
