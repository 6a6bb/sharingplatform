package com.littlea.sharingplatform.bo;

import com.littlea.sharingplatform.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenqiting
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectRoleResultBo {
    private Integer roleId;
    private List<User> userList;
}
