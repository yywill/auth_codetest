package com.hsbc.codetest.auth.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.service.AuthService;
import com.hsbc.codetest.auth.service.RepoService;
import com.hsbc.codetest.auth.service.RoleService;
import com.hsbc.codetest.auth.service.UserService;
import com.hsbc.codetest.auth.utils.TokenUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Singleton
 *
 */
public class AuthServiceImpl implements AuthService {
    private static final Logger log = Logger.getLogger(AuthServiceImpl.class);
    private static Gson gson = new GsonBuilder().create();

    private static RoleService roleService;
    private static UserService userService;
    private static AuthService instance;
    // key -> sessionId
    private static NavigableMap<String, Session> store;

    static class Session implements Comparable<Session>{
        String id; //sessionId in uuid format
        String userName; // redundant data. userName is unique and unmodifiable to be cached.
        long timestamp; // expire at unix time(ms)
        String token; // cache to avoid re-calculation

        // tree map to evict old key(future features)
        @Override
        public int compareTo(Session o) {
            if(this.timestamp == o.timestamp){
                return 0;
            }else if(this.timestamp > o.timestamp){
                return 1;
            }else{
                return -1;
            }
        }
    }

    static {
        instance = new AuthServiceImpl();
        store = new TreeMap<>();
        roleService = new RoleServiceImpl();
        userService = new UserServiceImpl();
    }
    public long ttl = AuthService.defaultTTL;

    private AuthServiceImpl(){
        super();
    }

    @Override
    public void addRole(String userName, String roleName) {
        User user = userService.get(userName);
        if(user==null){
            return ;
        }

        Role role = roleService.get(roleName);
        if(role==null){
            try {
                role = roleService.create(roleName);
            } catch (AlreadyExistException e) {
                role = roleService.get(roleName);
            }
        }
        user.addRole(role);
    }

    @Override
    public String auth(String name, String password) {
        User user = userService.get(name);
        if(user==null){
            return null;
        }else{
            if(user.validate(password)){
                //generate token
                Session session = new Session();
                session.id = UUID.randomUUID().toString();;
                session.timestamp = System.currentTimeMillis()+ttl;
                session.userName = name;
                String token = TokenUtil.encrypt(gson.toJson(session));
                session.token = token;
                store.put(session.id, session);
                return token;
            }else{
                return null;
            }
        }
    }

    @Override
    public void invalidate(String token) {
        String json = TokenUtil.decrypt(token);
        if(json==null) return;
        Session session = gson.fromJson(json, Session.class);
        String id = session.id;
        if(id==null) return;
        store.remove(id);
    }

    @Override
    public boolean check(String token, String roleName) {
        User user = getValidUser(token);
        if(user==null) return false;

        Role role = roleService.get(roleName);
        if(role!=null){
           return user.check(role);
        }else{
            return true;
        }
    }

    private User getValidUser(String token){
        String json = TokenUtil.decrypt(token);
        if(json==null) return null;

        Session session = gson.fromJson(json, Session.class);

        boolean valid = valid(session);
        if(!valid){
            return null;
        }

        User user = userService.get(session.userName);
        return user;
    }

    private boolean valid(Session session){
        if(!store.containsKey(session.id)) return false;

        long now = System.currentTimeMillis();
        long timestamp = session.timestamp;
        String userName = session.userName;

        boolean valid = false;

        if(userName!=null && timestamp>0){
            valid = timestamp+ttl>now;
        }

        if(!valid){
            store.remove(session.id);
        }
        return valid;
    }

    @Override
    public Set<Role> allRoles(String token) {
        User user = getValidUser(token);

        if(user==null){
            return null;
        }else{
            return user.getRoles();
        }
    }

    public static AuthService getInstance(){
        return instance;
    }

    public static AuthService getInstance(long ttl){
        if(ttl>0 && instance instanceof AuthServiceImpl){
            ((AuthServiceImpl)instance).ttl = ttl;
        }
        return instance;
    }

}
