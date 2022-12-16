package com.anderscore.skiller.coworker_qualification;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.dao.AbstractJpaDAO;
import com.anderscore.skiller.skill.Skill;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class CoworkerQualificationDAO extends AbstractJpaDAO<CoworkerQualification> {
    @PersistenceContext
    EntityManager entityManager;

    public CoworkerQualificationDAO() {
        setClazz(CoworkerQualification.class);
    }

    public List<Skill> findCoworkersQualificationById(String coworkerId) {
        return entityManager.createQuery("SELECT cw.skill FROM CoworkerQualification cw WHERE cast(cw.coworker.id as text) LIKE ?1")
                .setParameter(1, coworkerId).getResultList();
    }

    public List<Skill> findSkillsByCowoker(Long coworkerId, Date from) {
        return entityManager.createQuery(
                "SELECT cw.skill FROM CoworkerQualification cw WHERE cw.coworker.id = ?1 AND cw.acquiredAt >= ?2"
        ).setParameter(1, coworkerId).setParameter(2, from).getResultList();
    }

    public List<Coworker> getCoworkersBySkill(Long skillId) {
        return entityManager.createQuery(
                "SELECT cw.coworker " +
                        "FROM CoworkerQualification cw " +
                        "WHERE cw.skill.id = ?1"
        ).setParameter(1, skillId).getResultList();
    }
}
