package com.jian.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian
 * @since 2022-07-21
 */
@Getter
@Setter
@TableName("user")
@ApiModel(value = "User对象", description = "用户类")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    @TableField("userId")
    private String userId;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("头像")
    @TableField("image")
    private String image;

    @ApiModelProperty("注册时间")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty("用户信息修改时间")
    @TableField("updateTime")
    private Date updateTime;

    @ApiModelProperty("用户是否被禁用")
    @TableField("enable")
    private Integer enable;


}
