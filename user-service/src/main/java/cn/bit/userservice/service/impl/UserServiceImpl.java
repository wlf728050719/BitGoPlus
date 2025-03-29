package cn.bit.userservice.service.impl;


import cn.bit.common.pojo.dto.UserBaseInfoDTO;
import cn.bit.common.pojo.po.UserPO;
import cn.bit.common.pojo.vo.UserFavorVO;
import cn.bit.userservice.mapper.UserMapper;
import cn.bit.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserFavorVO getUserFavorById(int id) {
        UserPO userPO = userMapper.getUserPOById(id);
        if(userPO==null)
            return null;
        return new UserFavorVO(userPO);
    }

    @Override
    public UserBaseInfoDTO getUserBaseInfoById(int id) {
        UserPO userPO = userMapper.getUserPOById(id);
        if (userPO==null)
            return null;
        return new UserBaseInfoDTO(userPO);
    }
}
