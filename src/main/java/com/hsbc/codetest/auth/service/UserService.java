package com.hsbc.codetest.auth.service;

import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;

public interface UserService {
    User create(String name, String password) throws AlreadyExistException;
    void delete(String name) throws NotExistException;
    User get(String name);
}
