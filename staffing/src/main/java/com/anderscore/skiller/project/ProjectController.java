package com.anderscore.skiller.project;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.staffing.StaffingService;
import com.anderscore.skiller.staffing.StaffingService_Outdated;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/project")
public class ProjectController {
    private final StaffingService staffingService;

    @Autowired
    public ProjectController(StaffingService staffingService) {
        this.staffingService = staffingService;
    }

    @GetMapping
    public List<Project> getProjects() {
        return staffingService.getProjects();
    }

    // FA4 : Als Geschäftsführer muss ich Projekte (inkl. Zeitraum und benötigte Skills) verwalten können. Dies beinhaltet Projekte anlegen.
    @Transactional
    @PostMapping
    public void registerNewProject(@RequestBody Project project) {
        staffingService.addNewProject(project);
    }

    // FA4 : Als Geschäftsführer muss ich Projekte (inkl. Zeitraum und benötigte Skills) verwalten können. Dies beinhaltet Änderungen an Daten.
    @Transactional
    @PutMapping(path = "{projectId}")
    public void updateProject(@PathVariable Long projectId, @RequestParam(required = false) Date start, @RequestParam(required = false) Date end) {
        staffingService.updateProject(projectId, start, end);
    }

    // FA 4: Als Geschäftsführer muss ich Projekte (inkl. Zeitraum und benötigte Skills) verwalten können. Dies beinhaltet die Notwendigkeit neue Skills hinzuzufügen.
    @Transactional
    @PostMapping(path = "{projectId}/skills")
    public void addSkillsToProject(@PathVariable Long projectId, @RequestBody ProjectSkills projectSkills) {
        staffingService.addSkillsToProject(projectId, projectSkills.skillIds, projectSkills.neededFrom, projectSkills.neededTo);
    }

   // FA 5 : Als Geschäftsführer soll ich automatisch ermitteln können, welche Mitarbeiter für ein neues Projekt in Frage kommen.
    @Transactional
    @GetMapping(path = "/{projectId}/availableCoworkers")
    public List<Coworker> getAvailableCoworkersForProject(@PathVariable Long projectId) {
        return staffingService.getAvailableCoworkersForProject(projectId);
    }

    // FA 4: Als Geschäftsführer muss ich Projekte (inkl. Zeitraum und benötigte Skills) verwalten können. Dies beinhaltet Projekte zu archivieren.
    @Transactional
    @PostMapping(path = "{projectId}/archive")
    public void addProjectToArchive(@PathVariable Long projectId) {
        staffingService.addProjectToArchive(projectId);
    }

}
