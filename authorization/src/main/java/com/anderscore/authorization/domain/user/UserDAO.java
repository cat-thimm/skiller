package com.anderscore.authorization.domain.user;

import com.anderscore.authorization.dao.AbstractJpaDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO extends AbstractJpaDAO<User> {
    @PersistenceContext
    EntityManager entityManager;

    public UserDAO() {
        setClazz(User.class);
    }

    public List<User> findByName(String username) {
        return entityManager.createQuery("SELECT u " +
                "FROM User u " +
                "WHERE u.name = ?1").setParameter(1, username).getResultList();
    }
}
