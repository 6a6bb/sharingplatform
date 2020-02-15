package com.littlea.sharingplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.littlea.sharingplatform.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<String> selectByRoleId(@Param("roleId") Integer roleId);
}