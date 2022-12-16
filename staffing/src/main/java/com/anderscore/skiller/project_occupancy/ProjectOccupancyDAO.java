package com.anderscore.skiller.project_occupancy;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.dao.AbstractJpaDAO;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectOccupancyDAO extends AbstractJpaDAO<ProjectOccupancy> {
    @PersistenceContext
    EntityManager entityManager;

    public ProjectOccupancyDAO() {
        setClazz(ProjectOccupancy.class);
    }

    public List<Coworker> findAvailableCoworkers(Date from, Date to) {
        return  entityManager.createQuery("SELECT DISTINCT c " +
                        "FROM Coworker c " +
                        "FULL JOIN ProjectOccupancy po " +
                        "ON po.coworker.id = c.id " +
                        "WHERE ((po.startDate <= ?1 OR po.startDate >= ?2) AND (po.endDate >= ?1 OR po.endDate <= ?2) " +
                        "OR (po.startDate IS NULL AND po.endDate IS NULL))")
                .setParameter(1, from)
                .setParameter(2, to)
                .getResultList();
    }

    public List findAvailableCoworkersWithSkills(Long projectId, Date from, Date to) {
//        List res =  entityManager.createNativeQuery(
//                        "SELECT DISTINCT t1 " +
//                                "FROM " +
//                                "(SELECT DISTINCT c.id, c.first_name, c.last_name " +
//                                "FROM coworker c " +
//                                "FULL JOIN project_occupancy po " +
//                                "ON po.coworker_id = c.id " +
//                                "WHERE ((po.start_date <= :from OR po.start_date >= :to ) AND (po.end_date >= :from OR po.end_date <= :to ) " +
//                                "OR (po.start_date IS NULL AND po.end_date IS NULL))) t1 " +
//                            "INNER JOIN " +
//                                "(SELECT DISTINCT coworker.id, coworker.first_name, coworker.last_name " +
//                                "FROM coworker " +
//                                "INNER JOIN coworker_qualification cq " +
//                                "ON cq.coworker_id = coworker.id " +
//                                "INNER JOIN project_qualification pq " +
//                                "ON cq.skill_id = pq.skill_id " +
//                                "WHERE pq.project_id = :projectId) t2 " +
//                             "ON t1.id = t2.id")
//
//                .setParameter("from", from)
//                .setParameter("to", to)
//                .setParameter("projectId", projectId)
//                .unwrap(org.hibernate.query.NativeQuery.class)
//                .addScalar("t1", JsonNodeBinaryType.INSTANCE)
//                .getResultList();
//
//        System.out.println(res);
//
//        return List.of();

      //  return (List) coworkers;
        List res =  entityManager.createQuery(
                        "SELECT DISTINCT t1 " +
                                "FROM " +
                                "  (SELECT DISTINCT c.id, c.firstName, c.lastName " +
                                "FROM Coworker c " +
                                "FULL JOIN ProjectOccupancy po " +
                                "ON po.coworker.id = c.id " +
                                "WHERE ((po.startDate <= :from OR po.startDate >= :to ) AND (po.endDate >= :from OR po.endDate <= :to ) " +
                                "OR (po.startDate IS NULL AND po.endDate IS NULL))) t1 " +
                                "INNER JOIN " +
                                "(SELECT DISTINCT cw.id, cw.firstName, cw.lastName " +
                                "FROM Coworker cw" +
                                "INNER JOIN CoworkerQualification cq " +
                                "ON cq.coworker.id = cw.id " +
                                "INNER JOIN ProjectQualification pq " +
                                "ON cq.skill.id = pq.skill.id " +
                                "WHERE pq.project.id = :projectId) t2 " +
                                "ON t1.id = t2.id")

                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("projectId", projectId)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("t1", JsonNodeBinaryType.INSTANCE)
                .getResultList();

        System.out.println(res);

        return List.of();

    }
}
