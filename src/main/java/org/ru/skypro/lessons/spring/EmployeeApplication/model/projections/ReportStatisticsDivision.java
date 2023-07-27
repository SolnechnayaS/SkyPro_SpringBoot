package org.ru.skypro.lessons.spring.EmployeeApplication.model.projections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ReportStatisticsDivision {
    private static final Logger logger = LoggerFactory.getLogger(ReportStatisticsDivision.class);
    String divisionName;
    Long numberEmployees;
    Double sumSalary;
    Double maxSalary;
    Double minSalary;
    Double averageSalary;
    @Override
    public String toString() {
        return "ReportStatisticsDivision{" +
                "divisionName='" + divisionName + '\'' +
                ", numberEmployees=" + numberEmployees +
                ", sumSalary=" + sumSalary +
                ", maxSalary=" + maxSalary +
                ", minSalary=" + minSalary +
                ", averageSalary=" + averageSalary +
                '}';
    }

    public ReportStatisticsDivision(String divisionName, Long numberEmployees, Double sumSalary, Double maxSalary, Double minSalary, Double averageSalary) {
        this.divisionName = divisionName;
        this.numberEmployees = numberEmployees;
        this.sumSalary = (double) (Math.round(sumSalary*100))/100;
        this.maxSalary = (double) (Math.round(maxSalary*100))/100;
        this.minSalary = (double) (Math.round(minSalary*100))/100;
        this.averageSalary = (double) (Math.round(averageSalary*100))/100;
    }

    public static String serializeReportStatisticDivision(ReportStatisticsDivision reportStatisticsDivision) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(reportStatisticsDivision);
    }

    public static ReportStatisticsDivision deserializeReportStatisticDivision(String reportStatisticDivision) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(reportStatisticDivision, ReportStatisticsDivision.class);
    }
}
