package com.rrtx.simulationclient.longhu.security;

import com.alibaba.fastjson.JSON;
import com.rrtx.simulationclient.service.IHttpClient;
import com.rrtx.simulationclient.util.RSAEncrypt;
import com.rrtx.simulationclient.util.SHA256;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

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
    public void sha256() throws Exception{
        Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Base64 base64 = new Base64();

        dataMap.put("name", "lijing");
        dataMap.put("telNo", "17701084082");
        requestMap.put("data", dataMap);
        requestMap.put("mac", SHA256.getSHA256Str(JSON.toJSONString(dataMap)));

        String publicKey = RSAEncrypt.loadPublicKeyByFile("E:\\source\\github\\simulation\\pems\\");
        RSAPublicKey rsaPublicKey = RSAEncrypt.loadPublicKeyByStr(publicKey);

        System.out.println("Request string is: " + JSON.toJSONString(requestMap));
        List<String> subRequestStrList = separation(JSON.toJSONString(requestMap));
        String encryptStr = "";

        for(String subRequestStr: subRequestStrList){
            String encSubRequestStr = new String(base64.encode(RSAEncrypt.encrypt(rsaPublicKey, subRequestStr.getBytes())));
            encryptStr += encSubRequestStr;
            System.out.println(subRequestStr.length());
            System.out.println(encSubRequestStr.length());
        }

        System.out.println(encryptStr);

        String privateKey = RSAEncrypt.loadPrivateKeyByFile("E:\\source\\github\\simulation\\pems\\");
        RSAPrivateKey rsaPrivateKey = RSAEncrypt.loadPrivateKeyByStr(privateKey);
        List<String> subEncStrList = eSeparation(encryptStr);

        String decryptStr = "";
        for(String subEncStr: subEncStrList){
            String decSubStr = new String(RSAEncrypt.decrypt(rsaPrivateKey, base64.decode(subEncStr)));
            decryptStr += decSubStr;
            System.out.println(subEncStr.length());
            System.out.println(decSubStr.length());
        }

        System.out.println(decryptStr);

//        String result = (String) httpClient.post(url+ "securityTest", "formData=" + decryptStr, charset);
    }

    public List<String> separation(String src){
        List<String> result = new ArrayList<>();
        int num = src.length() / 100;
        int f = 0;
        while (num > f){
            result.add(src.substring(f*100, f*100 + 100));
            f++;
        }

        if(src.length() > f * 100){
            result.add(src.substring(f*100, src.length()));
        }

        return result;
    }

    public List<String> eSeparation(String src){
        List<String> result = new ArrayList<>();
        int num = src.length() / 172;
        int f = 0;
        while (num > f){
            result.add(src.substring(f*172, f*172 + 172));
            f++;
        }

        if(src.length() > f * 172){
            result.add(src.substring(f*172, src.length()));
        }

        return result;
    }
}
