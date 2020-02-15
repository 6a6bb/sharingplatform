package com.littlea.sharingplatform.service.impl;

import com.littlea.sharingplatform.bo.SelectRoleResultBo;
import com.littlea.sharingplatform.bo.UpdateUserRoleBo;
import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.constant.RoleConstant;
import com.littlea.sharingplatform.entity.User;
import com.littlea.sharingplatform.entity.UserInformation;
import com.littlea.sharingplatform.mapper.UserRoleMapper;
import com.littlea.sharingplatform.service.UserRoleService;
import com.littlea.sharingplatform.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author chenqiting
 */
@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleMapper userRoleMapper;
    @Override
    public Result updateUserRole(UserInformation userInformation, UpdateUserRoleBo updateUserRoleBo) {
        if (!userInformation.getRoleId().equals(RoleConstant.ADMIN)){
            return ResultConstant.NO_ROLE_UPDATE;
        }
        //检查参数
        if (Objects.isNull(updateUserRoleBo.getRoleId()) || CollectionUtils.isEmpty(updateUserRoleBo.getUserIdList())){
            return ResultConstant.ARG_ERROR;
        }

        //将一系列人员修改为RoleId对应的角色
        userRoleMapper.updateBatchByUserId(updateUserRoleBo);

        return ResultConstant.SUCCESS;
    }

    @Override
    public Result<SelectRoleResultBo> selectBatchByRoleId(Integer roleId, String teamGroup) {
        List<User> userList = userRoleMapper.selectListByRoleIdOrGroup(roleId, teamGroup);
        //设置结果返回
        SelectRoleResultBo selectRoleResultBo = new SelectRoleResultBo(roleId, userList);
        return new Result<>(selectRoleResultBo);
    }
}
