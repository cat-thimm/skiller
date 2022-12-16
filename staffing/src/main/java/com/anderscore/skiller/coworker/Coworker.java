package com.anderscore.skiller.coworker;

import com.anderscore.skiller.coworker_qualification.CoworkerQualification;
import com.anderscore.skiller.project_occupancy.ProjectOccupancy;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Coworker")
// für Hibernate (Mapping) - good Practice Namen explizit anzugeben / Klassenname länger als Entity name
@Table // für Tabelle in DB (Mapping)
public class Coworker implements Serializable {
    @Id
    @SequenceGenerator(
            name = "coworker_id_seq",
            sequenceName = "coworker_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "coworker_id_seq"
    )

    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "coworker")
    private final Set<CoworkerQualification> coworkerQualification = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "coworker")
    private final Set<ProjectOccupancy> projectOccupancies = new HashSet<>();

    public Coworker() {
    }

    public Coworker(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Konstruktur ohne ID, da diese von DB erzeugt wird
    public Coworker(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<CoworkerQualification> getCoworkerQualification() {
        return coworkerQualification;
    }

    public Set<ProjectOccupancy> getProjectOccupancies() {
        return projectOccupancies;
    }

    @Override
    public String toString() {
        return "Coworker{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
