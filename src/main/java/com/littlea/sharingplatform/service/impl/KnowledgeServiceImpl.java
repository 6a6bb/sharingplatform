package com.littlea.sharingplatform.service.impl;

import com.littlea.sharingplatform.bo.UploadMdStringBo;
import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.entity.Knowledge;
import com.littlea.sharingplatform.mapper.KnowledgeMapper;
import com.littlea.sharingplatform.service.KnowledgeService;
import com.littlea.sharingplatform.vo.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**dd
 * @author chenqiting
 */
@Service
@RequiredArgsConstructor
public class KnowledgeServiceImpl implements KnowledgeService {
    @NonNull
    private KnowledgeMapper knowledgeMapper;

    private String pathPrefix = ClassUtils.getDefaultClassLoader().getResource("static").getPath().substring(1);

    @Value("${file.imagePrefix}")
    private String imagePrefix;
    @Override
    public Result uploadMdString(UploadMdStringBo uploadMdStringBo, Integer userId) {
        if (Objects.isNull(uploadMdStringBo.getName()) || Objects.isNull(uploadMdStringBo.getContent())
            || Objects.isNull(uploadMdStringBo.getTeamGroup()) || Objects.isNull(uploadMdStringBo.getType())){
            return ResultConstant.ARGS_ERROR;
        }
        Knowledge knowledge = new Knowledge();
        //封装数据
        BeanUtils.copyProperties(uploadMdStringBo, knowledge);
        knowledge.setUploadDate(new Date(System.currentTimeMillis()));
        knowledge.setUploadUserId(userId);

        knowledgeMapper.insert(knowledge);

        return ResultConstant.SUCCESS;
    }

    @Override
    public Result<List<Knowledge>> findMdString(Integer type, String teamGroup) {
        return new Result<>(knowledgeMapper.selectByTypeAndTeamGroup(type, teamGroup));
    }

    @Override
    public Result uploadImage(MultipartFile file) {
        try{
            // 检测是否为图片
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(file.getInputStream());
            if (!ImageIO.getImageReaders(imageInputStream).hasNext()) {
                imageInputStream.close();
                return ResultConstant.FILE_ERROR;
            }
            imageInputStream.close();
            String filename = "/img/" + UUID.randomUUID() + ".jpg";

            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathPrefix + filename);
            //保存在本地
            Files.write(path, bytes);
            return new Result<>(imagePrefix + filename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultConstant.SYSTEM_ERROR;
        }
    }
}
