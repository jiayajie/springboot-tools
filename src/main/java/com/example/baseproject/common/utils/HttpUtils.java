package com.example.baseproject.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/31 10:45
 * @Version 1.0
 */
public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 请求相应地址, 会把jsonmap转换成json.通过jsonDate参数名传递
     * @param url
     * @param jsonMap
     * @return json字符串
     */
    public static String getRemotoDataWithJsonData(String url,Map<String,Object> jsonMap) throws Exception{

        String json = JSON.toJSONString(jsonMap);

        StringBuffer content=null;
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = null;
        //BufferedOutputStream output = null;

        InputStream bis = null;

        try {
            //请求超时
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
            // 读取超时
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);

            httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("jsonData",json));

            if(jsonMap!=null&&jsonMap.size()>0){
                jsonMap.entrySet().iterator();
            }


            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            HttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                bis = response.getEntity().getContent();
                List<String> list= IOUtils.readLines(bis);
                content=new StringBuffer();
                for(String c:list){
                    content.append(c);
                }
            }else{
                logger.info(EntityUtils.toString(response.getEntity()));
                return null;
            }

        } catch (Exception e) {
            throw new Exception(e);

        } finally {
            if (bis != null) {
                try {
                    bis.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                }
            }

            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return content.toString();
    }


    /**
     * 像一个对应地址发送http请求. map里的参数会一个个的当做post的参数传递
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String getRemotoHttpData(String url,Map<String,String> param) throws RuntimeException{

        String json = JSON.toJSONString(param);

        StringBuffer content=null;
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = null;
        //BufferedOutputStream output = null;

        InputStream bis = null;

        try {
            //请求超时
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
            // 读取超时
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);

            httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            if(param!=null&&param.size()>0){
                Iterator<Map.Entry<String,String>> it= param.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String,String> entry=  it.next();
                    params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
            }


            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            HttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                bis = response.getEntity().getContent();
                List<String> list= IOUtils.readLines(bis);
                content=new StringBuffer();
                for(String c:list){
                    content.append(c);
                }
            }else{
                logger.info(EntityUtils.toString(response.getEntity()));
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (bis != null) {
                try {
                    bis.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                }
            }

            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return content.toString();
    }

    /**
     * 像一个对应地址发送httpGet请求.
     * @param url
     * @return
     * @throws Exception
     */
    public static String getRemotoHttpGetData(String url) throws RuntimeException{


        StringBuffer content=null;
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;

        InputStream bis = null;

        try {
            //请求超时
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
            // 读取超时
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
            httpGet = new HttpGet(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //发起请求
            HttpResponse response = client.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                bis = response.getEntity().getContent();
                List<String> list= IOUtils.readLines(bis);
                content=new StringBuffer();
                for(String c:list){
                    content.append(c);
                }
            }else{
                logger.info(EntityUtils.toString(response.getEntity()));
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (bis != null) {
                try {
                    bis.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                }
            }

            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return content.toString();
    }
}
