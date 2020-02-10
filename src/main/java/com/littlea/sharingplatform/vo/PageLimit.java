package com.littlea.sharingplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Albumen
 */
@Data
@AllArgsConstructor
public class PageLimit {
    private Integer page;
    private Integer limit;
}
