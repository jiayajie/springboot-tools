package com.example.baseproject.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/12/27.
 */
public class UploadModel {
    private String extraField;

    private MultipartFile[] files;

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "UploadModel{" +
                "extraField='" + extraField + '\'' +
                ", files=" + Arrays.toString(files) +
                '}';
    }
}
