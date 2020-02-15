package com.littlea.sharingplatform.controller;

import com.littlea.sharingplatform.bo.UpdateUserRoleBo;
import com.littlea.sharingplatform.entity.UserInformation;
import com.littlea.sharingplatform.service.UserRoleService;
import com.littlea.sharingplatform.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenqiting
 */
@RestController
@AllArgsConstructor
public class UserRoleController {
    private UserRoleService userRoleService;
    @PreAuthorize("hasRole('ROLE_USER_ADMIN')")
    @PostMapping("/api/userAuthority/admin/update")
    public Result updateUserRole(@RequestBody UpdateUserRoleBo updateUserRoleBo,
                                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
        UserInformation userInformation = (UserInformation) usernamePasswordAuthenticationToken.getDetails();
        return userRoleService.updateUserRole(userInformation, updateUserRoleBo);
    }

    @PreAuthorize("hasRole('ROLE_USER_ADMIN')")
    @GetMapping("/api/userAuthority/admin/userMessage/get")
    public Result selectBatchByRoleId(@RequestParam Integer roleId,
                                      @RequestParam(required = false) String teamGroup){
        return userRoleService.selectBatchByRoleId(roleId, teamGroup);
    }
}
