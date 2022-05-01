package com.hsbc.codetest.auth.service;

import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;

public interface RoleService {
    Role create(String name) throws AlreadyExistException;
    void delete(String name) throws NotExistException;
    Role get(String name);
}
