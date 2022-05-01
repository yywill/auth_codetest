package com.hsbc.codetest.auth.service;

import com.hsbc.codetest.auth.domain.Domain;
import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;

public interface RepoService<T extends Domain> {
    T create(T t) throws AlreadyExistException;
    void delete(T t) throws NotExistException;
    T get(String name);
}
