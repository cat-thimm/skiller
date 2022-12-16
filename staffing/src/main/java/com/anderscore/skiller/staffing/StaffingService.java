package com.anderscore.skiller.staffing;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.coworker.CoworkerDAO;
import com.anderscore.skiller.coworker_qualification.CoworkerQualification;
import com.anderscore.skiller.coworker_qualification.CoworkerQualificationDAO;
import com.anderscore.skiller.project.Project;
import com.anderscore.skiller.project.ProjectDAO;
import com.anderscore.skiller.project_archive.ProjectArchive;
import com.anderscore.skiller.project_archive.ProjectArchiveDAO;
import com.anderscore.skiller.project_occupancy.ProjectOccupancyDAO;
import com.anderscore.skiller.project_qualification.ProjectQualification;
import com.anderscore.skiller.project_qualification.ProjectQualificationDAO;
import com.anderscore.skiller.skill.Skill;
import com.anderscore.skiller.skill.SkillDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class StaffingService {
    private final CoworkerDAO coworkerDAO;
    private final SkillDAO skillDAO;
    private final CoworkerQualificationDAO coworkerQualificationDAO;
    private final ProjectDAO projectDAO;
    private final ProjectQualificationDAO projectQualificationDAO;
    private final ProjectOccupancyDAO projectOccupancyDAO;
    private final ProjectArchiveDAO projectArchiveDAO;

    @Autowired
    public StaffingService(CoworkerDAO coworkerDAO,
                           SkillDAO skillDAO,
                           CoworkerQualificationDAO coworkerQualificationDAO,
                           ProjectDAO projectDAO,
                           ProjectQualificationDAO projectQualificationDAO,
                           ProjectOccupancyDAO projectOccupancyDAO,
                           ProjectArchiveDAO projectArchiveDAO) {
        this.coworkerDAO = coworkerDAO;
        this.skillDAO = skillDAO;
        this.coworkerQualificationDAO = coworkerQualificationDAO;
        this.projectDAO = projectDAO;
        this.projectQualificationDAO = projectQualificationDAO;
        this.projectOccupancyDAO = projectOccupancyDAO;
        this.projectArchiveDAO = projectArchiveDAO;
    }

    /*-----------------------------------------------Coworker Methods-------------------------------------------------*/
    public List<Coworker> getCoworkers() {
        return this.coworkerDAO.findAll();
    }

    public void addNewCoworker(Coworker coworker) {
        this.coworkerDAO.create(coworker);
    }

    public void deleteCoworker(Long coworkerId) {
        Coworker coworker = getSingleCoworker(coworkerId);
        checkForExistingCoworker(coworker, coworkerId);

        this.coworkerDAO.deleteById(coworkerId);
    }

    public Coworker getSingleCoworker(Long coworkerId) {
        Coworker coworker = getSingleCoworker(coworkerId);
        checkForExistingCoworker(coworker, coworkerId);

        return coworker;
    }

    /*----------------------------------------------Skill Methods-----------------------------------------------------*/

    public Skill getSingleSkill(Long skillId) {
        Skill skill = this.skillDAO.findOne(skillId);
        checkForExisitingSkill(skill, skillId);

        return skill;
    }

    public List<Skill> getSkills() {
        return this.skillDAO.findAll();
    }

    public void addNewSkill(Skill skill) {
        this.skillDAO.create(skill);
    }

    /*------------------------------------------------Project Methods-------------------------------------------------*/

    public List<Project> getProjects() {
        // TODO: dates are saved -1 day - check bug!
        return projectDAO.findAll();
    }

    public void addNewProject(Project project) {
        checkDate(project.getStart(), project.getEnd(), project);

        projectDAO.create(project);
    }

    public void updateProject(Long projectId, Date start, Date end) {
        Project project = projectDAO.findOne(projectId);

        checkForExisitingProject(project, projectId);

        checkDate(start, end, project);

        if (start != null && !Objects.equals(project.getStart(), start)) {
            project.setStart(start);
        }

        if (end != null && !Objects.equals(project.getEnd(), end)) {
            project.setEnd(end);
        }
    }

    public void addProjectToArchive(Long projectId) {
        Project project = projectDAO.findOne(projectId);

        checkForExisitingProject(project, projectId);



        ProjectArchive archivedProject = new ProjectArchive(project.getTitle(), project.getStart(), project.getEnd());

        projectArchiveDAO.create(archivedProject);

        projectDAO.deleteById(projectId);
    }

    /*----------------------------------------Coworker Qualification Methods-------------------------------------------*/

    public void addCoworkerQualification(Long coworkerId, List<Long> skills, Date acquiredAt) {
        Coworker coworker = coworkerDAO.findOne(coworkerId);

        checkForExistingCoworker(coworker, coworkerId);

        for(Long s: skills) {
            CoworkerQualification qualification = new CoworkerQualification();

            Skill skill = skillDAO.findOne(s);

            checkForExisitingSkill(skill, s);

            qualification.setCoworker(coworker);
            qualification.setSkill(skill);
            qualification.setAcquiredAt(acquiredAt);

            coworkerQualificationDAO.create(qualification);
        }

    }

    public List<Skill> getCoworkerQualification(Long coworkerId) {
        List<Skill> qualifications = coworkerQualificationDAO.findCoworkersQualificationById(coworkerId.toString());
        if (qualifications.size() == 0) {
            throw new IllegalStateException("No coworker qualifications for coworker with coworker id " + coworkerId + " found");
        }

        return qualifications;
    }

    public List<Skill> getNewSkillsFromCoworker(Long coworkerId, Date from) {
        List <Skill> skills = coworkerQualificationDAO.findSkillsByCowoker(coworkerId, from);
        if(skills.isEmpty()) {
            throw new IllegalStateException("No new skills since " + from + " for coworker with coworker id " + coworkerId + " found");
        }
        return skills;
    }

    public List<Coworker> getCoworkersBySkill(Long skillId) {
        Skill skill = this.skillDAO.findOne(skillId);
        checkForExisitingSkill(skill, skillId);

        return coworkerQualificationDAO.getCoworkersBySkill(skillId);
    }

    /*---------------------------------------Project Qualification Methods--------------------------------------------*/
    public void addSkillsToProject(Long projectId, List<Long> skills, Date neededFrom, Date neededTo) {
        Project project = projectDAO.findOne(projectId);

        checkForExisitingProject(project, projectId);

        checkDate(neededFrom, neededTo, project);

        for (Long s : skills) {
            ProjectQualification projectQualification = new ProjectQualification();
            Skill skill = skillDAO.findOne(s);

            checkForExisitingSkill(skill, s);

            projectQualification.setSkill(skill);
            projectQualification.setProject(project);
            projectQualification.setNeededFrom(neededFrom);
            projectQualification.setNeededTo(neededTo);
            projectQualificationDAO.create(projectQualification);
        }
    }
    /*---------------------------------------Project Occupancy Methods------------------------------------------------*/
    public List<Coworker> getAvailableCoworkers(Date from, Date to) {
        return projectOccupancyDAO.findAvailableCoworkers( from, to);
    }

    public List<Coworker> getAvailableCoworkersForProject(Long projectId) {
        Project project = projectDAO.findOne(projectId);

        checkForExisitingProject(project, projectId);

        return projectOccupancyDAO.findAvailableCoworkersWithSkills(projectId, project.getStart(), project.getEnd());
    }

    /*------------------------------------------------Helper Methods--------------------------------------------------*/
    static void checkDate(Date start, Date end, Project project) {
        if (start != null && start.after(project.getEnd())) {
            throw new IllegalStateException("Start date cannot be after end date!");
        }

        if (end != null && end.before(project.getStart())) {
            throw new IllegalStateException("End date cannot be before start date!");
        }
    }

    static void checkForExistingCoworker(Coworker coworker, Long coworkerId) {
        if (coworker == null) {
            throw new IllegalStateException("Coworker with id " + coworker + " does not exist");
        }
    }

    static void checkForExisitingSkill(Skill skill, Long skillId) {
        if (skill == null) {
            throw new IllegalStateException("Skill with id " + skillId + " does not exist");
        }
    }

    static void checkForExisitingProject(Project project, Long projectId) {
        if (project == null) {
            throw new IllegalStateException("Project with id " + projectId + " does not exist");
        }
    }

    static void checkForExisitingArchivedProject(ProjectArchive project, Long projectId) {
        if (project == null) {
            throw new IllegalStateException("Project with id " + projectId + " has not been archived yet");
        }
    }
}
