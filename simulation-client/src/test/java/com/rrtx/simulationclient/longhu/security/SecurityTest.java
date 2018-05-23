package com.rrtx.simulationclient.longhu.security;

import com.alibaba.fastjson.JSON;
import com.rrtx.simulationclient.service.IHttpClient;
import com.rrtx.simulationclient.util.SHA256;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTest {
    @Value("${http.client.charset}")
    private String charset;

    @Value("${longhu.request.url}")
    private String url;

    @Autowired
    IHttpClient httpClient;

    @Test
    public void sha256(){
        Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "lijing");
        dataMap.put("telNo", "17701084082");
        requestMap.put("data", dataMap);
        requestMap.put("mac", SHA256.getSHA256Str(JSON.toJSONString(dataMap)));

        String result = (String) httpClient.post(url+ "securityTest", "formData=" + JSON.toJSONString(requestMap), charset);
    }
}
