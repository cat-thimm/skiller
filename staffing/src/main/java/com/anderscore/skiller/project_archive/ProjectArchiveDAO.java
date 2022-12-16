package com.anderscore.skiller.project_archive;

import com.anderscore.skiller.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectArchiveDAO extends AbstractJpaDAO<ProjectArchive> {
    public ProjectArchiveDAO() {
        setClazz(ProjectArchive.class);
    }
}
