package com.littlea.sharingplatform.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chenqiting
 */
@Data
@AllArgsConstructor
public class UserLoginResultBo {
    private Integer roleId;
    private String token;
}
