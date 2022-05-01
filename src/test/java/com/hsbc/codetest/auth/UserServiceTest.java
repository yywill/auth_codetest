package com.hsbc.codetest.auth;

import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;
import com.hsbc.codetest.auth.service.UserService;
import com.hsbc.codetest.auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceTest {

    UserService service = new UserServiceImpl();

    @Test
    public void testCreate() throws AlreadyExistException {
        User user = service.create("yy", "yyyy");
        assertThat(user).isNotNull();
    }

    @Test
    public void testCreateDuplicated() throws AlreadyExistException {
        User u1 = service.create("yy2", "yyyy");
        assertThatThrownBy(()->{
            service.create("yy2", "y423yyy");
        }).isInstanceOf(AlreadyExistException.class);
    }

    @Test
    public void testDelete() throws AlreadyExistException, NotExistException {
        User u1 = service.create("yy3", "yyyy");
        service.delete("yy3");
    }

    @Test
    public void testDeleteNotExist() throws AlreadyExistException, NotExistException {
        assertThatThrownBy(()->{
            service.delete("yyyy");
        }).isInstanceOf(NotExistException.class);
    }

    @Test
    public void testGet() throws AlreadyExistException, NotExistException {
        User u1 = service.create("yy5", "yyyy");
        service.delete("yy5");
        User u3 = service.create("yy6", "yyyy");
        assertThat(service.get("yy5")).isNull();
        assertThat(service.get("yy6")).isNotNull();
    }
}
