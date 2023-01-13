package com.alrawas.playjwt.service;


import com.alrawas.playjwt.domain.AppUser;
import com.alrawas.playjwt.domain.Role;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser appUser);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    AppUser getUser(String username);

    // in production do paging
    List<AppUser> getUsers();

}
