package com.example.baseproject.modules.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;

/**
 * Created by dongyaofeng on 2017/12/24.
 * 创建带结构的索引
 */

@Document(indexName = "bp_logs", type = "httplogs")
public class LogsIndex implements Serializable {

    @Id
    private Long id;

    @Field(index = FieldIndex.analyzed, store = true, type = FieldType.String)
    private String title;

    @Field(index = FieldIndex.analyzed, store = true, type = FieldType.String)
    private String subtitle;

    @Field(index = FieldIndex.analyzed, store = true, type = FieldType.String)
    private String text;

    @Field(index = FieldIndex.not_analyzed, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss", /*analyzer = "ik",*/ store = true, type = FieldType.Date)
    private String createTime;

    @Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
    private Integer count;

    @Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Boolean)
    private boolean isEssence;

    @Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isEssence() {
        return isEssence;
    }

    public void setEssence(boolean essence) {
        isEssence = essence;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
