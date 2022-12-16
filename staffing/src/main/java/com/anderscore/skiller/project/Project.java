package com.anderscore.skiller.project;

import com.anderscore.skiller.project_occupancy.ProjectOccupancy;
import com.anderscore.skiller.project_qualification.ProjectQualification;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Project")
@Table
public class Project implements Serializable {
    @Id
    @SequenceGenerator(
            name = "project_id_seq",
            sequenceName = "project_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_id_seq"
    )
    Long id;
    @Column(name = "title")
    String title;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date start;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date end;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private final Set<ProjectOccupancy> projectOccupancies = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private final Set<ProjectQualification> projectQualifications = new HashSet<>();

    public Project() {
    }

    public Project(Long id, String title, Date start, Date end) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public Project(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
