package com.example.baseproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created With IDEA.
 * 初始化 插件  (环境 Windows X64)
 *
 * @author dongyaofeng
 * @date 2017/12/25 13:21
 */
@Component
@ConfigurationProperties(prefix = "plugins")
public class InitPlugins {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean es;
    private boolean resid;

    public boolean isEs() {
        return es;
    }

    public void setEs(boolean es) throws Exception {
        this.es = es;
        //加载  es
        if (es) {
            initES("elasticsearch-2.3.3");
        }
    }

    public boolean isResid() {
        return resid;
    }

    public void setResid(boolean resid) {
        this.resid = resid;
        //加载 redis
        if (resid) {
            initES("redis-3.0.504");
        }

    }


    /**
     * 启动 插件
     */
    private void initES(String type) {
        String basepath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String path = basepath.substring(1, basepath.length());

        if ("elasticsearch-2.3.3".equals(type)) {
            BufferedReader bufferedReader = null;
            try {
                String espath = path + "plugins/es/elasticsearch-2.3.3/bin/elasticsearch.bat";
                Runtime runtime = Runtime.getRuntime();
                Process process = runtime.exec(espath);
                String line = null;
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                while ((line = bufferedReader.readLine()) != null) {
                    log.info("[ElasticSearch-2.3.3] " + line.substring(line.indexOf("]") + 1, line.length()));
                    if (line.indexOf("started") != -1) {
                        bufferedReader.close();
                        return;
                    }
                }
            } catch (IOException e) {

                try {
                    log.error("[ElasticSearch-2.3.3] init error .....!!!");
                    bufferedReader.close();
                } catch (IOException e1) {
                    bufferedReader = null;
                }
            }
        }
        if ("redis-3.0.504".equals(type)) {
            BufferedReader bufferedReader = null;
            try {
                String redispath = path + "plugins/redis/redis-3.0.504/redis-server.exe";
                Process process = Runtime.getRuntime().exec(redispath);
                String line = null;
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                while ((line = bufferedReader.readLine()) != null) {
                    log.info("[" + type + "] " + line);
                    if (line.indexOf("ready") != -1) {
                        bufferedReader.close();
                        return;
                    }

                }
            } catch (IOException e) {
                try {
                    log.error("[" + type + "] init error .....!!!");
                    bufferedReader.close();
                } catch (IOException e1) {
                    bufferedReader = null;
                }
            }
        }
    }
}
