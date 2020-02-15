package com.littlea.sharingplatform.bo;

import lombok.Data;

import java.util.List;

/**
 * @author chenqiting
 */
@Data
public class UpdateUserRoleBo {
    Integer roleId;
    List<String> userIdList;
}
