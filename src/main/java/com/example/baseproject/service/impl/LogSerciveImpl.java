package com.example.baseproject.service.impl;

import com.example.baseproject.model.es.LogsIndex;
import com.example.baseproject.repostitory.LogsRepostitory;
import com.example.baseproject.service.LogSercive;
import org.elasticsearch.bootstrap.Elasticsearch;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dongyaofeng on 2017/12/25.
 */
@Service
public class LogSerciveImpl implements LogSercive {

    @Autowired
    private LogsRepostitory logsRepostitory;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void saveLog() {
        LogsIndex logsIndex = new LogsIndex();
        logsIndex.setId(System.currentTimeMillis());
        logsIndex.setCount(100);
        logsIndex.setTitle("这是标题");
        logsIndex.setCreateTime(new Date());
        logsIndex.setEssence(true);
        logsIndex.setState(0);
        logsRepostitory.save(logsIndex);
    }
}
