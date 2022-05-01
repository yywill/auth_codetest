package com.hsbc.codetest.auth.service.impl;

import com.hsbc.codetest.auth.domain.Domain;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;
import com.hsbc.codetest.auth.service.RepoService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class RepoServiceImpl<T extends Domain> implements RepoService<T> {

    private Map<String, T> store;

    protected RepoServiceImpl(){
        super();
        store = new HashMap<>();
    }

    @Override
    public T create(T t) throws AlreadyExistException{
        if(store.containsKey(t.getName())){
            throw new AlreadyExistException();
        }else{
            store.put(t.getName(),t);
        }
        return t;
    }

    @Override
    public void delete(T t) throws NotExistException {
        if(!store.containsKey(t.getName())){
            throw new NotExistException();
        }else{
            store.remove(t.getName());
        }
    }

    @Override
    public T get(String name) {
        return store.get(name);
    }
}
