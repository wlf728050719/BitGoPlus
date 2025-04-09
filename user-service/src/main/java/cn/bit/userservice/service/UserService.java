package cn.bit.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.bit.pojo.po.UserPO;

public interface UserService extends IService<UserPO> {
    int register(UserPO userPO);
    long count();
}
