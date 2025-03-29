package cn.bit.userservice.service;


import cn.bit.common.pojo.dto.UserBaseInfoDTO;
import cn.bit.common.pojo.vo.UserFavorVO;

public interface UserService {
    UserFavorVO getUserFavorById(int id);

    UserBaseInfoDTO getUserBaseInfoById(int id);
}
