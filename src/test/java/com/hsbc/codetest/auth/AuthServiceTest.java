package com.hsbc.codetest.auth;

import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.service.AuthService;
import com.hsbc.codetest.auth.service.RoleService;
import com.hsbc.codetest.auth.service.UserService;
import com.hsbc.codetest.auth.service.impl.AuthServiceImpl;
import com.hsbc.codetest.auth.service.impl.RoleServiceImpl;
import com.hsbc.codetest.auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthServiceTest {

    static UserService userService = new UserServiceImpl();
    static RoleService roleService = new RoleServiceImpl();

    AuthService service = AuthServiceImpl.getInstance();

    static Role role1;
    static Role role2;

    static User user1;
    static User user2;
    static User user3;

    static {
        try {
            user1 = userService.create("yy1","pwd1");
            user2 = userService.create("yy2","pwd2");
            user3 = userService.create("yy3","pwd3");

            role1 = roleService.create("role1");
            role2 = roleService.create("role2");

            user2.addRole(role2);
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testAddRole() throws AlreadyExistException {
        service.addRole("yy1","role1");
        User u1 = userService.get("yy1");
        assertThat(u1.getRoles()).contains(role1);
    }

    @Test
    public void testAuth() throws AlreadyExistException {
        String token = service.auth("yy1", "wrong pwd");
        assertThat(token).isNull();

        token = service.auth("yy1", "pwd1");
        assertThat(token).isNotNull();
    }

    @Test
    public void testCheck() throws AlreadyExistException {
        String token = service.auth("yy2", "pwd2");
        assertThat(token).isNotNull();

        //pre-config in static{}
        assertThat(service.check(token,"role2")).isTrue();

        assertThat(service.check(token,"role1")).isFalse();
    }

    @Test
    public void testInvalidate() throws AlreadyExistException {
        String token = service.auth("yy2", "pwd2");
        assertThat(token).isNotNull();

        //pre-config in static{}
        assertThat(service.check(token,"role2")).isTrue();

        service.invalidate(token);

        assertThat(service.check(token,"role2")).isFalse();
    }

    @Test
    public void testTTL() throws AlreadyExistException, InterruptedException {

        ((AuthServiceImpl)service).ttl = 1*1000; // 1 second
        String token = service.auth("yy2", "pwd2");
        assertThat(token).isNotNull();

        //pre-config in static{}
        assertThat(service.check(token,"role2")).isTrue();

        Thread.sleep(2*1000);

        assertThat(service.check(token,"role2")).isFalse();

    }

}
