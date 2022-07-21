package com.jian.admin.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.admin.entity.User;
import com.jian.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (ObjectUtils.isEmpty(user)){
            return null;
        }

        // TODO:还要查找用户的权限信息，封装到MyUser中

        return new MyUser(user.getUsername(),user.getPassword(),null);
    }
}
