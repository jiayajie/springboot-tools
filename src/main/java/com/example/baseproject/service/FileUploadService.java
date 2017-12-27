package com.example.baseproject.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface FileUploadService {


    void saveUploadedFiles(List<MultipartFile> multipartFiles) throws IOException;
}
