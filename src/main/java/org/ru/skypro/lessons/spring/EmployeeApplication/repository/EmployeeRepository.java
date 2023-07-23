package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long>,
        PagingAndSortingRepository<Employee, Long>{

    @Query(value = "SELECT * FROM employees",
            nativeQuery = true)
    List<Employee> findAllEmployee();

    @Query("SELECT new org.ru.skypro.lessons.spring.EmployeeApplication.model.projections." +
            "EmployeeFullInfo(e.name , e.salary , p.positionName) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p")
    List<EmployeeFullInfo> findAllEmployeeFullInfo();

    @Query(value = "SELECT * FROM employees WHERE employee_id= :id",
            nativeQuery = true)
    Employee getEmployeeById(@Param("id") Long id);

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
            "AND e.salary>= :salary")
    List<EmployeeFullInfo> salaryHigherThan(@Param("salary") Double salary);

    @Query(value = "SELECT * FROM employees WHERE division_id= :divisionId",
            nativeQuery = true)
    List<Employee> findAllEmployeeFromDivision(@Param("divisionId") Integer divisionId);

}
