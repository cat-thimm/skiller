package com.anderscore.reporting.report;

import com.anderscore.reporting.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDAO extends AbstractJpaDAO<Report> {
    public ReportDAO() {
        setClazz(Report.class);
    }
}
