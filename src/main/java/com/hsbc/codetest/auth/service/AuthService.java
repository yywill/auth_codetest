package com.hsbc.codetest.auth.service;

import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.domain.User;

import java.util.Set;

public interface AuthService {
    long defaultTTL = 2*60*60*1000;
    void addRole(String userName, String roleName);
    String auth(String name, String password);
    void invalidate(String token);
    boolean check(String token, String roleName);
    Set<Role> allRoles(String token);
}
