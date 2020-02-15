package com.littlea.sharingplatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenqiting
 * @date 2019/11/27 22:16
 */
@Data
@TableName("role")
public class Role {
    @TableId("role_id")
    private Integer roleId;
    @TableField("role_name")
    private String roleName;
}