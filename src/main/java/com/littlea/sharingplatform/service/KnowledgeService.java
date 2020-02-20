package com.littlea.sharingplatform.service;

import com.littlea.sharingplatform.bo.UploadMdStringBo;
import com.littlea.sharingplatform.vo.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenqiting
 */
public interface KnowledgeService {
    /***
     * 上传md字符串
     * @param uploadMdStringBo 上传
     * @param userId 添加字符串
     * @return
     */
    Result uploadMdString(UploadMdStringBo uploadMdStringBo, Integer userId);

    /***
     * 查找找md文件信息
     * @param type  类别
     * @param teamGroup 组别
     * @return
     */
    Result findMdString(Integer type, String teamGroup);

    /***
     * 上传照片
     * @param file
     * @return 返回照片的http连接
     */
    Result uploadImage(MultipartFile file);
}
