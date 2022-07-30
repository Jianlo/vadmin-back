package com.jian.admin.controller;


import com.jian.admin.dto.UserDto;
import com.jian.admin.entity.User;
import com.jian.admin.service.UserService;
import com.jian.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian
 * @since 2022-07-21
 */

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public R register(@RequestBody @Validated UserDto userDto){

        User user = new User();

        // TODO:用户id生成
        user.setUserId("888");
        user.setUsername(userDto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setImage("http://image/profile.jpg");

        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        user.setEnable(1);


        userService.save(user);
        return R.success().message("注册成功！");

    }

}

