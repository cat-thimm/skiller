package com.anderscore.authorization.service;

import com.anderscore.authorization.domain.role.Role;
import com.anderscore.authorization.domain.role.RoleDAO;
import com.anderscore.authorization.domain.user.User;
import com.anderscore.authorization.domain.user.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor // erstellt Konstruktur wo alle Klassenvariablen gesetzt werden
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userDAO.findByName(username);
        if (users.isEmpty()) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found in the database : {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        users.get(0).getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(users.get(0).getName(), users.get(0).getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user " + user.getName() + " to the data base");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.create(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role " + role.getName() + " to the data base");
        return roleDAO.create(role);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        log.info("Adding new role with " + roleId + " to user with id " + userId);
        User user = userDAO.findOne(userId);
        Role role = roleDAO.findOne(roleId);
        if(user == null) {
            throw new IllegalStateException("User with id " + userId + "does not exist");
        }
        if(role == null) {
            throw new IllegalStateException("Role with id " + roleId + "does not exist");
        }
        user.getRole().add(role);
    }

    @Override
    public User getUser(Long userId) {
        log.info("Fetching user with id " + userId);
        return userDAO.findOne(userId);
    }

    @Override
    public User getUserByName(String username) {
        List<User> users = userDAO.findByName(username);
        return users.get(0);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userDAO.findAll();
    }




}
