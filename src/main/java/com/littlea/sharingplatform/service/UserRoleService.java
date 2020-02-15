package com.littlea.sharingplatform.service;

import com.littlea.sharingplatform.bo.SelectRoleResultBo;
import com.littlea.sharingplatform.bo.UpdateUserRoleBo;
import com.littlea.sharingplatform.entity.UserInformation;
import com.littlea.sharingplatform.vo.Result;


/**
 * @author chenqiting
 */
public interface UserRoleService {
    /**
     * 功能描述 修改用户的权限
     * @param userInformation 修改人的userInformation
     * @param updateUserRoleBo 更新的Bo
     * @return  修改的结果
     * @author chenqiting
     * @date 2020/2/11 20:09
     */
     Result updateUserRole(UserInformation userInformation, UpdateUserRoleBo updateUserRoleBo);
    /**
     * 功能描述 根据roleId查询为该角色的所有成员
     * @param roleId
     * @param teamGroup
     * @return roleId, List<String>
     * @author chenqiting
     * @date 2020/2/11 20:10
     */
     Result<SelectRoleResultBo> selectBatchByRoleId(Integer roleId, String teamGroup);
}
