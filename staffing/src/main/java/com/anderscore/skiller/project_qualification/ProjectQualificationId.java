package com.anderscore.skiller.project_qualification;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProjectQualificationId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long project_id;
    private Long skill_id;

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long coworker_id) {
        this.project_id = coworker_id;
    }

    public Long getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(Long skill_id) {
        this.skill_id = skill_id;
    }
}
