package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "Common")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("Upload file")
    public Result<String> upload(MultipartFile file) {
        log.info("upload file:{}", file);
        try {
            String originalFilename = file.getOriginalFilename();
            //get original file name suffix
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;

            String path = aliOssUtil.upload(file.getBytes(), fileName);//get file's web link
            return Result.success(path);
        } catch (IOException e) {
            log.info("upload file error:{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
