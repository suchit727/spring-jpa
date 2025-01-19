package com.practice.spring_boot_query_practice.repository;

import com.practice.spring_boot_query_practice.entity.DepartmentEntity;
import com.practice.spring_boot_query_practice.entity.Employee;
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

    public List<Employee> getAllEmployee() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Employee> getAllEmployeeByName(String department) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        query.select(root).where(cb.equal(root.get("name"), department));
        return entityManager.createQuery(query).getResultList();
    }

    public List<Employee> getAllEmployeesByName(String department, Integer minSalary) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), minSalary);
        query.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("name"), department),
                        predicate
                )
        );
        return entityManager.createQuery(query).getResultList();
    }

    public List<Employee> getAllEmployeeSortedBySalary() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        query.select(root).orderBy(cb.desc(root.get("salary")));
        return entityManager.createQuery(query).getResultList();
    }

    public List<Employee> getAllEmployeeSortedByName() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get("name")));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

        public List<Employee> getAllEmployeeInPaginatedForm(Integer pageNumber, Integer size) {
            pageNumber-=pageNumber;
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);

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
        Root<Employee> root = query.from(Employee.class);

        query.select(cb.countDistinct(root.get("name")));
        return entityManager.createQuery(query).getSingleResult();
    }

    public Integer getHighestPaidEmployee() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<Employee> root = query.from(Employee.class);

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

    public List<Employee> getEmployeeAboveAverageSalary() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        Subquery<Double> subquery = query.subquery(Double.class);
        Root<Employee> rootSubQuery = subquery.from(Employee.class);
        subquery.select(criteriaBuilder.avg(rootSubQuery.get("salary")));


        query.select(root)
                .where(criteriaBuilder.greaterThan(root.get("salary"), subquery));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Employee> getEmployeeWithDynamicQuery(String name, Integer salary) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
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
