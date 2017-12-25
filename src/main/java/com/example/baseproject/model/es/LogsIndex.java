package com.example.baseproject.model.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by dongyaofeng on 2017/12/24.
 *
 * 创建带结构的索引
 */

@Document(indexName = "logs", type = "log", shards = 5, replicas = 1, indexStoreType = "fs", refreshInterval = "-1")
public class LogsIndex {

    @Id
    private Long id;

    @Field(index = FieldIndex.analyzed, store = true, type = FieldType.String)
    private String title;

    @Field(index = FieldIndex.analyzed, store = true, type = FieldType.String)
    private String subtitle;

    @Field(index = FieldIndex.analyzed, store = true, type = FieldType.String)
    private String text;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    @Field(index = FieldIndex.not_analyzed, /*analyzer = "ik",*/ store = true, type = FieldType.Date)
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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
