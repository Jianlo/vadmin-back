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
 * @since 2023-05-05
 */
@Getter
@Setter
@TableName("role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("角色名称中文")
    @TableField("nameZh")
    private String nameZh;

    @ApiModelProperty("角色创建时间")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty("角色修改时间")
    @TableField("updateTime")
    private Date updateTime;

    @ApiModelProperty("是否被禁用")
    @TableField("enable")
    private Integer enable;


}
