package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Report;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;
import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Integer>,
        PagingAndSortingRepository<Report, Integer> {

    @Query(value = "SELECT MAX(report_id) FROM report",
            nativeQuery = true)
    Long findLastReportId ();

    @Query("SELECT new org.ru.skypro.lessons.spring.EmployeeApplication.model.projections." +
            "ReportStatisticsDivision(d.divisionName, " +
            "COUNT(e.id), " +
            "SUM(e.salary), " +
            "MAX(e.salary), " +
            "MIN(e.salary), " +
            "AVG(e.salary)) " +
            "from Employee e " +
            "join fetch Division d " +
            "WHERE d.divisionId= :divisionId " +
            "group by d.divisionName")
    ReportStatisticsDivision reportStatisticsByDivision(@Param("divisionId") Integer divisionId);

    @Query("SELECT new org.ru.skypro.lessons.spring.EmployeeApplication.model.projections." +
            "ReportStatisticsDivision(d.divisionName, " +
            "COUNT(e.id), " +
            "SUM(e.salary), " +
            "MAX(e.salary), " +
            "MIN(e.salary), " +
            "AVG(e.salary)) " +
            "from Employee e " +
            "join fetch Division d " +
            "group by d.divisionName")
    List<ReportStatisticsDivision> reportStatisticsAllDivisions();

    @Query(value = "SELECT file_path FROM report " +
            "WHERE report_id= :reportId",
            nativeQuery = true)
    String findFilePathByReportId(@Param ("reportId") Integer reportId);

}
