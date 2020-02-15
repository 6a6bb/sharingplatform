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
@TableName("user_role")
public class UserRole {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("role_id")
    private Integer roleId;
}