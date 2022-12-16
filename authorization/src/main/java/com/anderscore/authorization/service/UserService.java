package com.anderscore.authorization.service;

import com.anderscore.authorization.domain.role.Role;
import com.anderscore.authorization.domain.user.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(Long userId, Long roleId);
    User getUser(Long userId);
    List<User> getUsers();

    User getUserByName(String username);
}
