package com.jian.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.admin.entity.Menu;
import com.jian.admin.entity.Role;
import com.jian.admin.entity.User;
import com.jian.admin.mapper.UserMapper;
import com.jian.admin.service.MenuService;
import com.jian.admin.service.RoleService;
import com.jian.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian
 * @since 2022-07-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuService menuService;


    /**
     * 获取用户权限信息
     * @param userId
     * @return
     */
    @Override
    public String getAuthorities(String userId) {

        //todo:将权限信息存入redis缓存

        String authorities = "";

        //获取用户角色信息
        String role_auth = "";
        List<Role> roleList = roleService.list(new QueryWrapper<Role>().inSql("id", "select roleId form user_role where userId = " + userId));
        for (Role role : roleList) {
            String roleName = "ROLE_" + role.getName();
            role_auth = role_auth.concat(",").concat(roleName);
        }

        //获取用户权限信息
        String perm_auth = "";
        List<String> menuIds = userMapper.getPerms(userId);
        for (String menuId : menuIds) {
            Menu menu = menuService.getOne(new QueryWrapper<Menu>().eq("id", menuId));
            perm_auth = perm_auth.concat(",").concat(menu.getPermission());

        }

        authorities = role_auth.concat(",").concat(perm_auth);

        return authorities;
    }

    //todo:清除用户缓存
}
