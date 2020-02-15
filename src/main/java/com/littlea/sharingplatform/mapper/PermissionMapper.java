package com.littlea.sharingplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.littlea.sharingplatform.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Set;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 功能描述 根据roleId获取该角色拥有的权限
     * @param roleId 角色id
     * @return url地址的集合
     * @author chenqiting
     * @date 2020/2/11 12:30
     */
    Set<String> selectByRoleId(@Param("roleId") Integer roleId);
}