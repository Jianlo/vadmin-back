package com.jian.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.admin.dto.UserDto;
import com.jian.admin.entity.Role;
import com.jian.admin.entity.User;
import com.jian.admin.entity.UserRole;
import com.jian.admin.service.RoleService;
import com.jian.admin.service.UserRoleService;
import com.jian.admin.service.UserService;
import com.jian.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

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

    /**
     * 根据用户id获取用户信息，并关联该用户的角色信息
     * @param userId
     * @return
     */

    @PreAuthorize("hasAuthority('sys:user:list')")  //设置具有相应权限才能访问改接口
    @GetMapping("/{userId}")
    public R getUserById(@PathVariable String userId){
        User user = userService.getOne(new QueryWrapper<User>().eq("userId", userId));
        if(user != null){
            List<Role> roleList = roleService.list(new QueryWrapper<Role>().inSql("id", "select roleId from user_role where userId = " + userId));
            user.setRoleList(roleList);
        }

        return R.success().data(user);
    }

    /**
     * 根据用户名分页查询用户信息
     * @param current
     * @param size
     * @param username
     * @return
     */

    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list")
    public R list(@RequestParam int current, @RequestParam int size, @RequestParam String username){

        Page<User> page = new Page<>(current, size);
        Page<User> userPage = userService.page(page, new QueryWrapper<User>().like("username", username));
        List<User> records = userPage.getRecords();
        for (User record : records) {
            List<Role> roleList = roleService.list(new QueryWrapper<Role>().inSql("id", "select roleId from user_role where userId = " + record.getUserId()));
            record.setRoleList(roleList);
        }

        return R.success().data(userPage);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */

    @PreAuthorize("hasAuthority('sys:user:save')")
    @PostMapping("/save")
    public R save(@Validated @RequestBody User user){

        user.setUserId("userid");
        user.setPassword(new BCryptPasswordEncoder().encode("88888888"));
        user.setImage("image");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        userService.save(user);
        return R.success();
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */

    @PreAuthorize("hasAuthority('sys:user:update')")
    @PostMapping("/update")
    public R update(@Validated @RequestBody User user){

        user.setUpdateTime(new Date());
        userService.update(new UpdateWrapper<User>().eq("userId",user.getUserId()));
        return R.success().data(user);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @Transactional //数据库事务
    @PostMapping("/delete")
    public R delete(String[] ids){

        //删除用户信息
        userService.remove(new QueryWrapper<User>().in("userId",ids));

        //删除用户角色信息
        userRoleService.remove(new QueryWrapper<UserRole>().in("userId",ids));
        return R.success();
    }


    /**
     * 为用户分配权限
     * @param userId
     * @param ids
     * @return
     */

    @PreAuthorize("hasAuthority('sys:user:role')")
    @Transactional
    @PostMapping("/rolePerm/{userId}")
    public R rolePerm(@PathVariable String userId, @RequestBody String[] ids){

        //删除之前已经分配的角色
        userRoleService.remove(new QueryWrapper<UserRole>().eq("userId",userId));

        //重新分配角色
        for (String id : ids) {

            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(id);
            userRoleService.save(userRole);
        }

        //Todo:删除缓存

        return R.success();
    }

    /**
     * 重置密码
     * @param userId
     * @return
     */

    @PreAuthorize("hasAuthority('sys:user:repass')")
    @GetMapping("/repass/{userId}")
    public R repass(@PathVariable String userId){

        User user = userService.getOne(new QueryWrapper<User>().eq("userId", userId));
        user.setUpdateTime(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode("88888888"));

        userService.updateById(user);

        return R.success();
    }

}

