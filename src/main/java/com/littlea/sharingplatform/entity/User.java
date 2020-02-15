package com.littlea.sharingplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;
    @TableField("user_name")
    private String userName;
    @TableField("user_password")
    private String userPassword;
    @TableField("email")
    private String email;
    @TableField("team_group")
    private String teamGroup;
}