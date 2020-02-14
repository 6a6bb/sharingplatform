package com.littlea.sharingplatform.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ：LinXinRan
 * @date ：Created in 2019/10/23 16:52
 */
@Component
@Data
public class ConfigProperties {

    /**
     * 文件夹绝对位置
     */
    @Value("${file.folderRealPath}")
    public String folderRealPath;

    /**
     * 文件夹相对位置
     */
    @Value("${file.folderPath}")
    public String folderPath;

}
