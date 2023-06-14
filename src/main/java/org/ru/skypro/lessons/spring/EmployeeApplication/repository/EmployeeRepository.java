package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>,
        PagingAndSortingRepository<Employee, Integer> {

    @Query(value = "SELECT * FROM employees",
            nativeQuery = true)
    List<Employee> findAllEmployee();
    @Query(value = "SELECT * FROM employees",
            nativeQuery = true)
    List<EmployeeView> findAllEmployeeView();

    @Query(value = "SELECT * FROM employees",
            nativeQuery = true)
    List<EmployeeInfo> findAllEmployeeInfo();

    @Query("SELECT new org.ru.skypro.lessons.spring.EmployeeApplication.model.projections." +
            "EmployeeFullInfo(e.name , e.salary , p.positionName) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p")
    List<EmployeeFullInfo> findAllEmployeeFullInfo();

    @Query(value = "SELECT * FROM employees WHERE employee_id= :id",
            nativeQuery = true)
    Employee getEmployeeById(@Param("id") Integer id);

    @Query(value = "SELECT MAX(salary) FROM employees;",
            nativeQuery = true)
    Integer maxSalary();

    @Query("SELECT new org.ru.skypro.lessons.spring.EmployeeApplication.model.projections." +
            "EmployeeFullInfo(e.name , e.salary , p.positionName) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p " +
            "AND e.salary=(SELECT MAX (e.salary) FROM Employee e)")
    List<EmployeeFullInfo> getEmployeeFullInfoWithMaxSalary();

    @Query("SELECT new org.ru.skypro.lessons.spring.EmployeeApplication.model.projections." +
            "EmployeeFullInfo(e.name , e.salary , p.positionName) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p " +
            "AND e.salary> :salary")
    List<EmployeeFullInfo> salaryHigherThan(@Param("salary") Integer salary);

}
