package com.hsbc.codetest.auth;

import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.domain.User;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.exception.NotExistException;
import com.hsbc.codetest.auth.service.RoleService;
import com.hsbc.codetest.auth.service.UserService;
import com.hsbc.codetest.auth.service.impl.RoleServiceImpl;
import com.hsbc.codetest.auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoleServiceTest {

    RoleService service = new RoleServiceImpl();

    @Test
    public void testCreate() throws AlreadyExistException {
        Role o1 = service.create("yy");
        assertThat(o1).isNotNull();
    }

    @Test
    public void testCreateDuplicated() throws AlreadyExistException {
        Role o1 = service.create("yy2");
        assertThatThrownBy(()->{
            service.create("yy2");
        }).isInstanceOf(AlreadyExistException.class);
    }

    @Test
    public void testDelete() throws AlreadyExistException, NotExistException {
        Role o1 = service.create("yy3");
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
        Role o1  = service.create("yy5");
        service.delete("yy5");
        Role o3 = service.create("yy6");
        assertThat(service.get("yy5")).isNull();
        assertThat(service.get("yy6")).isNotNull();
    }
}
