package com.anderscore.reporting.report;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "report")
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "coworker_id")
    private Long coworkerId;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;
    private String report;

    public Report() {
    }

    public Report(Long id, Long coworkerId, Date startDate, String report) {
        this.id = id;
        this.coworkerId = coworkerId;
        this.startDate = startDate;
        this.report = report;
    }

    public Report(Long coworkerId, Date startDate, String report) {
        this.coworkerId = coworkerId;
        this.startDate = startDate;
        this.report = report;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoworkerId() {
        return coworkerId;
    }

    public void setCoworkerId(Long coworkerId) {
        this.coworkerId = coworkerId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", coworkerId=" + coworkerId +
                ", startDate=" + startDate +
                ", report='" + report + '\'' +
                '}';
    }
}
