package com.anderscore.skiller.coworker;

import com.anderscore.skiller.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

@Repository
public class CoworkerDAO extends AbstractJpaDAO<Coworker> {
    public CoworkerDAO() {
        setClazz(Coworker.class);
    }
}
