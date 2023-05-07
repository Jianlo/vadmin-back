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
import com.jian.servicebase.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    RedisUtil redisUtil;


    /**
     * 获取用户权限信息并存入redis缓存
     * @param userId
     * @return
     */
    @Override
    public String getAuthorities(String userId) {

        String authorities = "";

        //从redis缓存获取权限信息
        if (redisUtil.hasKey("authority" + userId)){
            authorities = (String) redisUtil.get("authority" + userId);
        }
        else {

            //获取用户角色信息
            String role_auth = "";
            List<Role> roleList = roleService.list(new QueryWrapper<Role>().inSql("id", "select roleId form user_role where userId = " + userId));
            if(roleList.size() > 0){
                role_auth = roleList.stream().map(r -> "ROLE" + r.getName()).collect(Collectors.joining(","));
            }

            //获取用户权限信息
            String perm_auth = "";
            List<String> menuIds = userMapper.getPerms(userId);
            List<Menu> menus = menuService.listByIds(menuIds);
            if (menus.size() > 0){
                perm_auth = menus.stream().map(Menu::getPermission).collect(Collectors.joining(","));
            }

            authorities = role_auth.concat(",").concat(perm_auth);

            //将权限信息存入redis缓存
            redisUtil.set("authority" + userId,authorities,60*60);
        }

        return authorities;
    }


    /**
     * 清除用户权限信息的redis缓存
     * @param userId
     */
    @Override
    public void clearAuthorities(String userId) {
        redisUtil.del("authority" + userId);
    }


}
