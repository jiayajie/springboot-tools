package com.example.baseproject.repostitory;

import com.example.baseproject.model.es.LogsIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by dongyaofeng on 2017/12/25.
 */
public interface LogsRepostitory extends ElasticsearchRepository<LogsIndex, Long> {
}
