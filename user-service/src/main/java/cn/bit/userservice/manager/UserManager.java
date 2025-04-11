package cn.bit.userservice.manager;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.bit.pojo.po.UserPO;

public interface UserManager extends IService<UserPO> {
    Long insert(UserPO userPO);
    UserPO selectUserByUserName(String userName);
    UserPO selectUserByUserId(Long userId);
}
