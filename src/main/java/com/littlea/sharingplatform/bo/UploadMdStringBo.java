package com.littlea.sharingplatform.bo;

import lombok.Data;

/**
 * @author chenqiting
 */
@Data
public class UploadMdStringBo {
    private String name;

    private String teamGroup;

    private String content;
    /**
     * 1就是学习路线，2就是正常md文件
     */
    private Integer type;
}
