package com.anderscore.skiller.project_occupancy;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProjectOccupancyId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long coworker_id;
    private Long project_id;

    public Long getCoworker_id() {
        return coworker_id;
    }

    public void setCoworker_id(Long coworker_id) {
        this.coworker_id = coworker_id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }
}
