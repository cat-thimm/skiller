package com.anderscore.skiller.skill;

import com.anderscore.skiller.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

@Repository
public class SkillDAO extends AbstractJpaDAO<Skill> {
    public SkillDAO() {
        setClazz(Skill.class);
    }
}
