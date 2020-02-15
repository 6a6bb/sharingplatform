package com.littlea.sharingplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author chenqiting
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {

    private Integer  userId;

    private Integer  roleId;

    private Set<String> permission;
}
