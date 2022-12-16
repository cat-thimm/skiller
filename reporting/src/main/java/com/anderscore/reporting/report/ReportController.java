package com.anderscore.reporting.report;

import com.anderscore.reporting.service.ReportingService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/report")
public class ReportController {
    private final ReportingService reportingService;

    @Autowired
    public ReportController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @Transactional
    @GetMapping
    public List<Report> getReports() {
        return reportingService.getReports();
    }

    @Transactional
    @PostMapping()
    public void createReport(HttpServletRequest request, @RequestBody ReportRequest reportRequest) throws IOException, ParseException {
        reportingService.createReport(request, reportRequest.coworkerId, reportRequest.from);
    }


}

