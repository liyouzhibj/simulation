package com.rrtx.simulationclient.controller.longhu;

import com.alibaba.fastjson.JSON;
import com.rrtx.simulationclient.service.IHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterContoller {
    @Value("${http.client.charset}")
    private String charset;

    @Value("${longhu.request.url}")
    private String url;

    @Autowired
    IHttpClient httpClient;

    @RequestMapping(path = "/weChatRegist", method = RequestMethod.POST)
    public String weChatRegist(@RequestBody Map<String, Object> requestMap){
        String userId = "";
        String userName = "";

        if (requestMap.get("userId") != null) {
            userId = requestMap.get("userId").toString();
        }

        if (requestMap.get("userName") != null) {
            userName = requestMap.get("userName").toString();
        }

        return (String)httpClient.post(url+ "WsSPLoginAuth", JSON.toJSONString(requestMap), charset);
    }
}
