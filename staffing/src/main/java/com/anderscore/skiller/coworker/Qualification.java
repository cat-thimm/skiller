package com.anderscore.skiller.coworker;

import com.anderscore.skiller.skill.Skill;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Date;
import java.util.List;

public class Qualification {
    public List<Long> skills;
    @Temporal(TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date acquiredAt;
}
