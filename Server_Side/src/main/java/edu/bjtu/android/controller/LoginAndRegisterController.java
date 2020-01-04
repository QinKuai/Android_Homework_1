package edu.bjtu.android.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import edu.bjtu.android.dao.ElearnUserMapper;
import edu.bjtu.android.entity.ElearnUser;
import edu.bjtu.android.value.ResponseValue;

@Controller
@RequestMapping("/elearn")
@ResponseBody
public class LoginAndRegisterController {
	@Autowired
	private ElearnUserMapper userDao; 
	@Value("${headerurl.default}")
	private String defaultHeaderURL;
	
	private final static Logger LOGGER = LogManager.getFormatterLogger(LoginAndRegisterController.class); 
	
	@RequestMapping("/login")
	public String login(@Valid ElearnUser user, Errors errors) {
		JSONObject json = new JSONObject();
		if (errors.hasErrors()) {
			LOGGER.info("Http Request Params Error");
			json.put(ResponseValue.KEY, ResponseValue.ERROR);
			return json.toJSONString();
		}
		
		// 校验用户是否存在
		ElearnUser userCheck = userDao.selectByUsername(user.getUsername());
		if (userCheck == null) {
			json.put(ResponseValue.KEY, ResponseValue.USERERROR);
			return json.toJSONString();
		}
		
		// 校验用户密码
		if (!userCheck.getPassword().equals(user.getPassword())) {
			json.put(ResponseValue.KEY, ResponseValue.USERERROR);
			return json.toJSONString();
		}
		
		json.put("user", userCheck);
		
		LOGGER.info("User Login: " + user.getUsername());
		json.put(ResponseValue.KEY, ResponseValue.OK);
		return json.toJSONString();
	}
	
	@RequestMapping("/register")
	public String register(@Valid ElearnUser user, Errors errors) {
		JSONObject json = new JSONObject();
		if (errors.hasErrors()) {
			LOGGER.info("Http Request Params Error");
			json.put(ResponseValue.KEY, ResponseValue.ERROR);
			return json.toJSONString();
		}
		
		// 校验用户是否存在
		ElearnUser userCheck = userDao.selectByUsername(user.getUsername());
		if (userCheck != null) {
			LOGGER.info("User Exist Username: " + user.getUsername());
			json.put(ResponseValue.KEY, ResponseValue.USEREXIST);
			return json.toJSONString();
		}
		
		// 设置默认头像
		user.setHeaderURL(defaultHeaderURL);
		
		// 设置用户注册类型
		user.setType("n");
		user.setOpenid(null);
		
		try {
			userDao.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Data Error In User: " + user.getUsername());
			json.put(ResponseValue.KEY, ResponseValue.ERROR);
			return json.toJSONString();
		}
		
		ElearnUser returnUser = userDao.selectByUsername(user.getUsername());
		json.put("user", returnUser);
		
		LOGGER.info("User Register: " + user.getUsername());
		json.put(ResponseValue.KEY, ResponseValue.OK);
		return json.toJSONString();
	}
	
	@RequestMapping("/qqlogin")
	public String qqLogin(String openid, String username, String headerurl) {
		JSONObject json = new JSONObject();
		
		// 查找用户是否已存在
		ElearnUser userCheck = userDao.selectByOpenid(openid);
		if (userCheck != null) {
			LOGGER.info("User Login: " + userCheck.getUsername());
			json.put(ResponseValue.KEY, ResponseValue.OK);
			json.put("user", userCheck);
			return json.toJSONString();
		}
		
		// 检查用户名是否已存在
		ElearnUser usernameCheck = userDao.selectByUsername(username);
		if (usernameCheck != null) {
			username += "temp13467";
		}
		
		ElearnUser newUser = new ElearnUser();
		newUser.setUsername(username);
		newUser.setPassword("123456");
		newUser.setType("q");
		newUser.setOpenid(openid);
		newUser.setHeaderURL(headerurl);
		
		try {
			userDao.insert(newUser);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Data Error In User: " + username);
			json.put(ResponseValue.KEY, ResponseValue.ERROR);
			return json.toJSONString();
		}
		
		LOGGER.info("Login Through QQ User: " + username);
		json.put("user", newUser);
		json.put(ResponseValue.KEY, ResponseValue.OK);
		return json.toJSONString();
	}
}
