package com.anderscore.skiller.project_occupancy;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.project.Project;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "project_occupancy")
public class ProjectOccupancy implements Serializable {
    @EmbeddedId
    private ProjectOccupancyId id = new ProjectOccupancyId();

    @ManyToOne
    @MapsId("project_id")
    private Project project;

    @ManyToOne
    @MapsId("coworker_id")
    private Coworker coworker;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    public ProjectOccupancyId getId() {
        return id;
    }

    public void setId(ProjectOccupancyId id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Coworker getCoworker() {
        return coworker;
    }

    public void setCoworker(Coworker coworker) {
        this.coworker = coworker;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
