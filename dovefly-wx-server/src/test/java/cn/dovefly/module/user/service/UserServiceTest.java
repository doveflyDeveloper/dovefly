package cn.dovefly.module.user.service;

import cn.dovefly.test.common.BaseTest;
import cn.dovefly.module.user.entity.User;
import org.junit.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {

    @Resource
    private IUserService userService;

    @Test
    public void testQueryById() throws Exception {
        int bookId = 1000;
        User book = userService.getUserById(bookId);
        System.out.println(book);
    }

    @Test
    public void testAdd() throws Exception {
        int update = userService.add(new User());
        System.out.println("update=" + update);
    }

}