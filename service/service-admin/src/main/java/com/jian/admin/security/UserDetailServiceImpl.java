package com.jian.admin.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.admin.entity.User;
import com.jian.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;



@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户名不存在！");
        }

        // TODO:还要查找用户的权限信息，封装到MyUser中
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        return new MyUser(user.getUsername(),user.getPassword(),list);
    }
}
