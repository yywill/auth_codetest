package com.hsbc.codetest.auth.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User extends Domain {
    private String password;
    private Set<Role> roles;

    public Set<Role> getRoles(){
        if(roles==null){
            roles = new HashSet<>();
        }
        return Collections.unmodifiableSet(roles);
    }

    public boolean addRole(Role role){
        if(roles==null){
            roles = new HashSet<>();
        }
        return roles.add(role);
    }

    public boolean check(Role role){
       return roles.contains(role);
    }

    public boolean validate(String password) {
        return password.equals(this.password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
