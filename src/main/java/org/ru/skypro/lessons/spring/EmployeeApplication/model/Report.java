package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report {

    private static final Logger logger = LoggerFactory.getLogger(Report.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

//    @Column
//    String jsonTextReport;

    @Column
    String filePath;

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
//                ", jsonTextReport='" + jsonTextReport + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public Report(
//            String jsonTextReport,
            String filePath) {
//        this.jsonTextReport = jsonTextReport;
        this.filePath = filePath;
        logger.info("Создан отчет");
    }

}
