package com.littlea.sharingplatform.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 文件信息
 * @author ：LinXinRan
 * @date ：2020/2/9 21:35
 */
@AllArgsConstructor
@Data
public class FileMessage {
    /**
     * 上传时间
     */
    private String uploadTime;

    /**
     * 绝对位置
     */
    private String fileRealPath;

    /**
     * 相对位置
     */
    private String filePath;
}
