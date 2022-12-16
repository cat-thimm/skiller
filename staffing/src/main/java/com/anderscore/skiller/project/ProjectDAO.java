package com.anderscore.skiller.project;

import com.anderscore.skiller.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDAO extends AbstractJpaDAO<Project> {
    public ProjectDAO() {
        setClazz(Project.class);
    }
}
