package com.anderscore.skiller.skill;

import com.anderscore.skiller.coworker_qualification.CoworkerQualification;
import com.anderscore.skiller.project_qualification.ProjectQualification;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Skill")
@Table
public class Skill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "skill")
    private Set<CoworkerQualification> coworkerQualification = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "skill")
    private final Set<ProjectQualification> projectQualifications = new HashSet<>();

    public Skill() {
    }

    public Skill(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Skill(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CoworkerQualification> getCoworkerQualification() {
        return coworkerQualification;
    }


    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
