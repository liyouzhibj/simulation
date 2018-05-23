package com.rrtx.simulationclient.service.impl;

import com.rrtx.simulationclient.service.IHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpClientImpl implements IHttpClient<String, String , String, String>{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${http.client.content}")
    private String contentType;

    @Value("${http.client.charset}")
    private String charset;

    @Override
    public String get(String s, String s2, String s3) {
        return null;
    }

    @Override
    public String post(String url, String params, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", contentType);

        StringEntity stringEntity = new StringEntity(params, charset);
        stringEntity.setContentEncoding(charset);
        httpPost.setEntity(stringEntity);
        logger.info("Executing request: " + httpPost.getRequestLine());

        CloseableHttpResponse httpResponse = null;
        String result = "";
        try{
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, charset);
        }catch (IOException e){
            logger.error(e.getMessage());
            return "error";
        }finally {
            if(httpResponse != null){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    return "error";
                }
            }
        }

        return result;
    }
}
