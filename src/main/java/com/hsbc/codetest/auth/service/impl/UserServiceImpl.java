package com.hsbc.codetest.auth.service.impl;

import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;
import com.hsbc.codetest.auth.service.RepoService;
import com.hsbc.codetest.auth.service.UserService;

public class UserServiceImpl implements UserService {

    private static RepoService<User> repoService = new RepoServiceImpl<>();

    @Override
    public User create(String name, String password) throws AlreadyExistException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return repoService.create(user);
    }

    @Override
    public void delete(String name) throws NotExistException {
        User user = new User();
        user.setName(name);
        repoService.delete(user);
    }

    @Override
    public User get(String name) {
        return repoService.get(name);
    }
}
