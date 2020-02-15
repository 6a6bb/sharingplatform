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
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "description")
    private String description;
}