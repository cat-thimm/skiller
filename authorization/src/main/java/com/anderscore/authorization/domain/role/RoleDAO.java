package com.anderscore.authorization.domain.role;

import com.anderscore.authorization.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO extends AbstractJpaDAO<Role> {
    public RoleDAO() {
        setClazz(Role.class);
    }
}
