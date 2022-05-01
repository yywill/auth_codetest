package com.hsbc.codetest.auth.service.impl;

import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;
import com.hsbc.codetest.auth.service.RepoService;
import com.hsbc.codetest.auth.service.RoleService;

public class RoleServiceImpl implements RoleService {

    private static RepoService<Role> repoService = new RepoServiceImpl<>();

    @Override
    public Role create(String name) throws AlreadyExistException {
        Role role = new Role();
        role.setName(name);
        return repoService.create(role);
    }

    @Override
    public void delete(String name) throws NotExistException {
        Role role = new Role();
        role.setName(name);
        repoService.delete(role);
    }

    @Override
    public Role get(String name) {
        return repoService.get(name);
    }
}
