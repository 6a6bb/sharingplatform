package com.littlea.sharingplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.littlea.sharingplatform.bo.UpdateUserRoleBo;
import com.littlea.sharingplatform.entity.User;
import com.littlea.sharingplatform.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述
 * @author chenqiting
 * @date 2020/1/14 11:30
 */
@Mapper
public interface UserRoleMapper  extends BaseMapper<UserRole> {
    /**
     * 功能描述 用userId查询roleId
     * @param userId
     * @return roleId
     * @author chenqiting
     * @date 2020/2/10 16:58
     */
    Integer selectByUserId(@Param("userId") Integer userId);
    /**
     * 功能描述 批量更新权限
     * @param updateUserRoleBo 更新用户的权限为新的权限
     * @return 返回更新是否成功的次数
     * @author chenqiting
     * @date 2020/2/11 20:39
     */
    int updateBatchByUserId(@Param("updateUserRole") UpdateUserRoleBo updateUserRoleBo);
    /**
     * 功能描述 批量查询用户信息
     * @param roleId
     * @param teamGroup
     * @return 查询到的user的信息
     * @author chenqiting
     * @date 2020/2/11 20:55
     */
    List<User> selectListByRoleIdOrGroup(@Param("roleId") Integer roleId, @Param("teamGroup") String teamGroup);
}