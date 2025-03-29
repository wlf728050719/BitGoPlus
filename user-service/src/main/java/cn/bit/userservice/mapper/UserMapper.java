package cn.bit.userservice.mapper;


import cn.bit.common.pojo.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    @Select("select * from tb_user where id = #{id}")
    UserPO getUserPOById(@Param("id") Integer id);
}
