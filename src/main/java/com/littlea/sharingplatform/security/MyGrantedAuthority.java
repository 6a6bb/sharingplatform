package com.littlea.sharingplatform.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author chenqiting
 */
@AllArgsConstructor
public class MyGrantedAuthority implements GrantedAuthority {

    private String authorityUrl;

    @Override
    public String getAuthority() {
        return this.authorityUrl;
    }

}
