package com.anderscore.skiller.staffing;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.coworker.CoworkerRepository;
import com.anderscore.skiller.coworker_qualification.CoworkerQualification;
import com.anderscore.skiller.coworker_qualification.CoworkerQualificationRepository;
import com.anderscore.skiller.project.Project;
import com.anderscore.skiller.project.ProjectRepository;
import com.anderscore.skiller.project_qualification.ProjectQualification;
import com.anderscore.skiller.project_qualification.ProjectQualificationRepository;
import com.anderscore.skiller.skill.Skill;
import com.anderscore.skiller.skill.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service // ist dasselbe wie eine Component, aber hat Semantik
public class StaffingService_Outdated {
    private final CoworkerRepository coworkerRepository;
    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;
    private final CoworkerQualificationRepository coworkerQualificationRepository;
    private final ProjectQualificationRepository projectQualificationRepository;

    @Autowired
    public StaffingService_Outdated(CoworkerRepository coworkerRepository,
                                    SkillRepository skillRepository,
                                    ProjectRepository projectRepository,
                                    CoworkerQualificationRepository coworkerQualificationRepository,
                                    ProjectQualificationRepository projectQualificationRepository
    ) {
        this.coworkerRepository = coworkerRepository;
        this.skillRepository = skillRepository;
        this.projectRepository = projectRepository;
        this.coworkerQualificationRepository = coworkerQualificationRepository;
        this.projectQualificationRepository = projectQualificationRepository;
    }

    /*-----------------------------------------------Coworker Methods-------------------------------------------------*/

    public List<Coworker> getCoworkers() {
        return coworkerRepository.findAll();
    }

    public Coworker getSingleCoworker(Long coworkerId) {
        return coworkerRepository.findById(coworkerId).orElseThrow(() -> new IllegalStateException("Coworker with id " + coworkerId + " does not exist"));
    }

    public void addNewCoworker(Coworker coworker) {
        coworkerRepository.save(coworker);
    }

    public void deleteCoworker(Long coworkerId) {

        boolean exists = coworkerRepository.existsById(coworkerId);
        if (!exists) {
            throw new IllegalStateException("Coworker with id " + coworkerId + " does not exist");
        }
        coworkerRepository.deleteById(coworkerId);
    }

    @Transactional // Entity geht in manage State
    public void updateCoworker(Long coworkerId, String firstName) {
        Coworker coworker = coworkerRepository.findById(coworkerId).orElseThrow(() -> new IllegalStateException("Coworker with id " + coworkerId + " does not exist"));

        if (firstName != null && firstName.length() > 0 && !Objects.equals(coworker.getFirstName(), firstName)) {
            coworker.setFirstName(firstName);
        }
    }

    /*----------------------------------------------Skill Methods-----------------------------------------------------*/

    public List<Skill> getSkills() {
        return this.skillRepository.findAll();
    }

    public void addNewSkill(Skill skill) {
        skillRepository.save(skill);
    }

    public Skill getSingleSkill(Long skillId) {
        return skillRepository.findById(skillId).orElseThrow(() -> new IllegalStateException("Skill with id " + skillId + " does not exist"));

    }

    /*------------------------------------------------Project Methods-------------------------------------------------*/
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public void addNewProject(Project project) {
        projectRepository.save(project);
    }

    @Transactional
    public void updateProject(Long projectId, Date start, Date end) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException("Project with id " + projectId + " does not exist"));

        StaffingService.checkDate(start, end, project);
    }

    /*----------------------------------------Coworker Qualification Methods-------------------------------------------*/
    public void addCoworkerQualification(Coworker coworker, Skill skill, Date acquiredAt) {
        CoworkerQualification qualification = new CoworkerQualification();

        qualification.setCoworker(coworker);
        qualification.setSkill(skill);
        qualification.setAcquiredAt(acquiredAt);

        coworkerQualificationRepository.save(qualification);
    }

    public List<CoworkerQualification> getCoworkerQualification(Long coworkerId) {

        List<CoworkerQualification> coworkerQualification = coworkerQualificationRepository.findCoworkerQualificationByCoworkerId(coworkerId);
        if (coworkerQualification.size() == 0) {
            throw new IllegalStateException("No coworker qualifications for coworker with coworker id " + coworkerId + " found");
        }

        return coworkerQualification;
    }

    public List<Coworker> getCoworkersBySkill(Long skillId) {
        List<CoworkerQualification> coworkerQualification = coworkerQualificationRepository.findCoworkerQualificationBySkillId(skillId);
        if (coworkerQualification.size() == 0) {
            throw new IllegalStateException("No coworkers with skill id " + skillId + " found");
        }
        List<Coworker> coworkerList = coworkerQualification.stream().map(CoworkerQualification::getCoworker).toList();
        return coworkerList;
    }

    /*-------------------------------------------Project Occupancy Methods---------------------------------------------*/

//    public List<CoworkerQualification> getCoworkerAvailability(Date from, Date to) {
//
//    }

    /*---------------------------------------Project Qualification Methods---------------------------------------------*/
    public void addSkillsToProject(Long projectId, List<Long> skills, Date neededFrom, Date neededTo) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException("Project with id " + projectId + " does not exist"));

       for(Long s: skills) {
           ProjectQualification projectQualification = new ProjectQualification();
           Skill skill = skillRepository.findById(s).orElseThrow(() -> new IllegalStateException("Skill with id " + s + " does not exist"));
           projectQualification.setSkill(skill);
           projectQualification.setProject(project);
           projectQualification.setNeededFrom(neededFrom);
           projectQualification.setNeededTo(neededTo);
           projectQualificationRepository.save(projectQualification);
       }
    }


}
