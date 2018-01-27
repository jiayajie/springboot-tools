package com.example.baseproject.modules.file.service.impl;

import com.example.baseproject.modules.file.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static String UPLOADED_FOLDER = "D://temp//";

    @Override
    public void saveUploadedFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();

            //判断当前目录是否存在.不存在则自动创建.
            File f = new File(UPLOADED_FOLDER);
            if (!f.exists() && !f.isDirectory()) {
                f.mkdirs();
            }

            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }
    }
}
