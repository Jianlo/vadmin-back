package com.jian.admin.entity;

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
 * @since 2023-05-06
 */
@Getter
@Setter
@TableName("menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @ApiModelProperty("菜单名称")
    @TableField("menuName")
    private String menuName;

    @ApiModelProperty("菜单对应权限")
    @TableField("permission")
    private String permission;

    @ApiModelProperty("菜单创建时间")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty("菜单修改时间")
    @TableField("updateTime")
    private Date updateTime;

    @ApiModelProperty("菜单是否被禁用")
    @TableField("enable")
    private Integer enable;


}
