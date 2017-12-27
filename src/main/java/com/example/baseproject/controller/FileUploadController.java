package com.example.baseproject.controller;

import com.example.baseproject.domain.ResultEntity;
import com.example.baseproject.domain.UploadModel;
import com.example.baseproject.service.FileUploadService;
import com.example.baseproject.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/12/27.
 */
@RestController
public class FileUploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 单文件上传
     */
    @PostMapping("/api/upload")
    public ResultEntity uploadFile(@RequestParam("file") MultipartFile uploadfile) {

        if (uploadfile.isEmpty()) {
            return ResultUtil.success("please select a file!");
        }

        try {
            fileUploadService.saveUploadedFiles(Arrays.asList(uploadfile));
        } catch (IOException e) {
            return ResultUtil.error(-1, "文件上传异常!!");
        }

        return ResultUtil.success("success!");
    }

    /**
     * 多文件上传
     */
    @PostMapping("/api/upload/multi")
    public ResultEntity uploadFileMulti(@RequestParam("extraField") String extraField, @RequestParam("files") MultipartFile[] uploadfiles) {

        //获取上传文件名称
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename()).filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return ResultUtil.error(0, "文件为空!");
        }

        try {
            fileUploadService.saveUploadedFiles(Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return ResultUtil.error(-1, "文件上传异常!!");
        }

        return ResultUtil.success("success!");

    }

    /**
     * 文件信息封装
     */
    @PostMapping("/api/upload/multi/model")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {
        try {
            fileUploadService.saveUploadedFiles(Arrays.asList(model.getFiles()));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
    }
}
