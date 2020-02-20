package com.littlea.sharingplatform.controller;

import com.littlea.sharingplatform.bo.UploadMdStringBo;
import com.littlea.sharingplatform.entity.Knowledge;
import com.littlea.sharingplatform.service.KnowledgeService;
import com.littlea.sharingplatform.vo.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenqiting
 */
@RestController
@AllArgsConstructor
public class KnowledgeController {
    private KnowledgeService knowledgeService;

    @PreAuthorize("hasRole('ROLE_SHARE')")
    @PostMapping("/api/knowledge/mdFile/upload")
    public Result uploadMdString(@RequestBody UploadMdStringBo uploadMdStringBo, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return knowledgeService.uploadMdString(uploadMdStringBo, Integer.valueOf(usernamePasswordAuthenticationToken.getName()));
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/knowledge/mdFile/get")
    public Result findMdString(@RequestParam Integer type, @RequestParam(required = false) String teamGroup) {
        return knowledgeService.findMdString(type, teamGroup);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/image/upload")
    public Result uploadPicture(@RequestPart("file") MultipartFile file) {
        return knowledgeService.uploadImage(file);
    }
}
