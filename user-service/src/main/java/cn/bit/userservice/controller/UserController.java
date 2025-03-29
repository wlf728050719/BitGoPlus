package cn.bit.userservice.controller;

import cn.bit.common.pojo.dto.UserBaseInfoDTO;
import cn.bit.common.pojo.vo.R;
import cn.bit.common.pojo.vo.UserFavorVO;
import cn.bit.userservice.config.PatternProperties;
import cn.bit.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PatternProperties patternProperties;

    @GetMapping("/test/{id}")
    public String test(@PathVariable Integer id) {
        System.out.println(id);
        return id.toString()+" "+LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDateformat()));
    }

    @GetMapping("/favor/{id}")
    public R getUserFavorById(@PathVariable Integer id) {
        UserFavorVO vo = userService.getUserFavorById(id);
        if(vo != null) {
            return R.ok(vo);
        }
        else
            return R.failed("用户不存在");
    }

    @GetMapping("/baseInfo/{id}")
    public R getUserBaseInfoById(@PathVariable Integer id, @RequestHeader(value = "source",required = false) String source) {
        System.out.println("get request");
        System.out.println(source);
        UserBaseInfoDTO dto = userService.getUserBaseInfoById(id);
        if(dto != null) {
            return R.ok(dto);
        }
        else
            return R.failed("用户不存在");
    }
}
