package cn.dovefly.module.user.controller;

import cn.dovefly.framework.schema.People;
import cn.dovefly.module.user.entity.User;
import cn.dovefly.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fengchunming on 2017/4/24.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private People people1;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, @PathVariable("id") Integer id) throws Exception {
        User user = userService.getUserById(id);
        System.out.println(people1.getName());
        return user;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object add(HttpServletRequest request, User user) throws Exception {
        user.setUserName("hhhhhhhhhhh");
        user.setPassword("1234567890");
        user.setAge(12);
        userService.add(user);
        return user;
    }

}
