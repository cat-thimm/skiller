package com.anderscore.skiller.coworker_qualification;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CoworkerQualificationId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long coworker_id;
    private Long skill_id;

    public Long getCoworker_id() {
        return coworker_id;
    }

    public void setCoworker_id(Long coworker_id) {
        this.coworker_id = coworker_id;
    }

    public Long getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(Long skill_id) {
        this.skill_id = skill_id;
    }
}
