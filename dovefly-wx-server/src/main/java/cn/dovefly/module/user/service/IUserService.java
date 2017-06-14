package cn.dovefly.module.user.service;

import cn.dovefly.module.user.entity.User;

/**
 * Created by fengchunming on 2017/4/24.
 */
public interface IUserService {
    int add(User info);
    User getUserById(int id);
}
