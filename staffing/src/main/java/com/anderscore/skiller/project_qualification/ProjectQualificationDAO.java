package com.anderscore.skiller.project_qualification;

import com.anderscore.skiller.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectQualificationDAO extends AbstractJpaDAO<ProjectQualification> {
    public ProjectQualificationDAO() {
        setClazz(ProjectQualification.class);
    }
}
