package edu.bjtu.android;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class Test {
	public static void main(String[] args) {
		String line = "{\r\n" + 
				"\"response\": {\r\n" + 
				"\"respCode\": \"200\",\r\n" + 
				"\"respMsg\": \"Access Success\"\r\n" + 
				"},\r\n" + 
				"\"user\": {\r\n" + 
				"\"header\": \"header\\\\1.jpg\",\r\n" + 
				"\"id\": 1,\r\n" + 
				"\"username\": \"QinKuai\"\r\n" + 
				"}\r\n" + 
				"}";
		JSONObject json = JSONObject.parseObject(line);
		
		System.out.println(JSONPath.eval(json, "$.user.id"));
	}
}
