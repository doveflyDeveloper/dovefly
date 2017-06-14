package cn.dovefly.module.user.service.impl;

import cn.dovefly.module.user.dao.UserMapper;
import cn.dovefly.module.user.entity.User;
import cn.dovefly.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fengchunming on 2017/4/24.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public int add(User info) {
        return userMapper.insert(info);
    }

    public User getUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
