package com.anderscore.skiller.project_qualification;


import com.anderscore.skiller.project.Project;
import com.anderscore.skiller.skill.Skill;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "project_qualification")
public class ProjectQualification implements Serializable {
    @EmbeddedId
    private ProjectQualificationId id = new ProjectQualificationId();

    @ManyToOne
    @MapsId("project_id")
    private Project project;

    @ManyToOne
    @MapsId("skill_id")
    private Skill skill;

    @Column(name = "needed_from")
    private Date neededFrom;

    @Column(name = "needed_to")
    private Date neededTo;

    public ProjectQualificationId getId() {
        return id;
    }

    public void setId(ProjectQualificationId id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Date getNeededFrom() {
        return neededFrom;
    }

    public void setNeededFrom(Date neededFrom) {
        this.neededFrom = neededFrom;
    }

    public Date getNeededTo() {
        return neededTo;
    }

    public void setNeededTo(Date neededTo) {
        this.neededTo = neededTo;
    }
}
