package com.anderscore.reporting.service;

import com.anderscore.reporting.report.Report;
import com.anderscore.reporting.report.ReportDAO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class ReportingService {
    private final ReportDAO reportDAO;

    @Autowired
    public ReportingService(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public WebClient localApiClient() {
        return WebClient.create("http://localhost:8080/api/v1");
    }

    public void createReport(HttpServletRequest request, Long coworkerId, String from) throws IOException, ParseException {
        System.out.println(from);
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header was not provided");
        }
        List<Skill> response = localApiClient()
                .get().uri("/coworker/" + coworkerId + "/skills?from=" + from)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Skill>>() {
                })
                .block();

        if (response == null) {
            throw new IllegalStateException("Could not get skills from Staffing service");
        }

        File htmlTemplateFile = new File("reporting/src/main/java/com/anderscore/reporting/service/report.html");
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);

        String body = convertToHTML(response);
        htmlString = htmlString.replace("$coworkerId", coworkerId.toString());
        htmlString = htmlString.replace("$from", from);
        htmlString = htmlString.replace("$body", body);

        String DEFAULT_PATTERN = "dd-MM-yyyy";
        DateFormat formatter = new SimpleDateFormat(DEFAULT_PATTERN);

        reportDAO.create(new Report(coworkerId, formatter.parse(from), htmlString));

        // for debbuging create file
        // File newHtmlFile = new File("reporting/src/main/java/com/anderscore/reporting/service/new.html");
        // FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

    public String convertToHTML(List<Skill> skills) {
        StringBuilder sb = new StringBuilder();
        for (Skill skill : skills) {
            System.out.println(skill);
            sb.append("<tr><td>").append(skill.name).append("</td></tr>");
        }
        return sb.toString();
    }

    public List<Report> getReports() {
        return reportDAO.findAll();
    }
}

