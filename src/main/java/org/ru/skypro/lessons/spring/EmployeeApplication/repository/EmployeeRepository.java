package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>,
        PagingAndSortingRepository<Employee, Integer> {

    @Query(value = "SELECT * FROM employee WHERE name= :name",
            nativeQuery = true)
    List<Employee> getEmployeesByName(@Param("name") String name);

    @Query(value = "SELECT * FROM employee",
            nativeQuery = true)
    List<EmployeeView> findAllEmployeeView();

    @Query(value = "SELECT * FROM employee",
            nativeQuery = true)
    List<EmployeeInfo> findAllEmployeeInfo();

    @Query("SELECT new org.ru.skypro.lessons.spring.EmployeeApplication.model.projections." +
            "EmployeeFullInfo(e.name , e.salary , p.positionName) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p")
    List<EmployeeFullInfo> findAllEmployeeFullInfo();

}
