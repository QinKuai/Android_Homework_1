package com.qinkuai.homework1;

import android.net.Uri;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.qinkuai.homework1.model.ResponseValue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest {
    @Before
    public void testInit(){
        System.out.println("Test Init...");
    }

    @After
    public void testEnd(){
        System.out.println("Test End.");
    }

    @Test
    public void testServerResponseValue() {
        String response = "{\"response\":{\"respCode\":\"201\",\"respMsg\":\"User Is Not Existed Or Password Error\"}}";
        JSONObject json = JSONObject.parseObject(response);
        // 待测返回值对象
        ResponseValue value = json.getObject("response", ResponseValue.class);
        // 预计值对象
        ResponseValue target = new ResponseValue();
        target.setRespCode("201");
        target.setRespMsg("User Is Not Existed Or Password Error");

        Assert.assertEquals(value, target);
    }

    @Test
    public void testNormalLoginJSONPath(){
        String jsonText = "{\"response\":{\"respCode\":\"200\",\"respMsg\":\"Access Success\"},\"user\":{\"header\":\"header/1.jpg\",\"id\":1,\"type\":\"n\",\"username\":\"QinKuai\"}}";
        JSONObject json = JSONObject.parseObject(jsonText);

        // 预计放回的数据
        String userID = JSONPath.eval(json, "$.user.id") + "";
        String userType = JSONPath.eval(json, "$.user.type") + "";
        String header = JSONPath.eval(json, "$.user.header") + "";
        String username = JSONPath.eval(json, "$.user.username") + "";

        Assert.assertEquals(userID, "1");
        Assert.assertEquals(userType, "n");
        Assert.assertEquals(header, "header/1.jpg");
        Assert.assertEquals(username, "QinKuai");
    }

    @Test
    public void testQQLoginJSONPath(){
        String jsonText = "{\"response\":{\"respCode\":\"200\",\"respMsg\":\"Access Success\"},\"user\":{\"header\":\"http://thirdqq.qlogo.cn/g?b=oidb&k=BCoWiax1boiamrw58al3RwlQ&s=100&t=1553175274\",\"id\":12,\"openid\":\"8D1AB3C355530E5697EA8A42DBEC69AD\",\"type\":\"q\",\"username\":\"QinKuaitemp13467\"}}";
        JSONObject json = JSONObject.parseObject(jsonText);

        // 预计放回的数据
        String userID = JSONPath.eval(json, "$.user.id") + "";
        String userType = JSONPath.eval(json, "$.user.type") + "";
        String header = JSONPath.eval(json, "$.user.header") + "";
        String username = JSONPath.eval(json, "$.user.username") + "";

        Assert.assertEquals(userID, "12");
        Assert.assertEquals(userType, "q");
        Assert.assertEquals(header, "http://thirdqq.qlogo.cn/g?b=oidb&k=BCoWiax1boiamrw58al3RwlQ&s=100&t=1553175274");
        Assert.assertEquals(username, "QinKuaitemp13467");
    }
}