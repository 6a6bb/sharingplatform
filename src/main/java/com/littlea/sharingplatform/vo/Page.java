package com.littlea.sharingplatform.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页通用类
 *
 * @author Albumen
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page<T> {
    private Integer size;
    private List<T> list;
}
