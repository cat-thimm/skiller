package com.anderscore.skiller.coworker_qualification;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.skill.Skill;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "coworker_qualification")
public class CoworkerQualification implements Serializable {
    @EmbeddedId
    private CoworkerQualificationId id = new CoworkerQualificationId();

    @ManyToOne
    @MapsId("coworker_id")
    private Coworker coworker;

    @ManyToOne
    @MapsId("skill_id")
    private Skill skill;

    @Column(name = "acquired_at")
    @Temporal(TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date acquiredAt;

    public CoworkerQualificationId getId() {
        return id;
    }

    public void setId(CoworkerQualificationId id) {
        this.id = id;
    }

    public Coworker getCoworker() {
        return coworker;
    }

    public void setCoworker(Coworker coworker) {
        this.coworker = coworker;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Date getAcquiredAt() {
        return acquiredAt;
    }

    public void setAcquiredAt(Date acquiredAt) {
        this.acquiredAt = acquiredAt;
    }
}

