package com.littlea.sharingplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenqiting
 * @date 2019/11/27 22:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_permission")
public class RolePermission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "permission_id")
    private Integer permissionId;
    @TableField(value = "role_id")
    private Integer roleId;
}