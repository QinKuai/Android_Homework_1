package edu.bjtu.android.value;

public class ResponseValue {
	public final static String KEY = "response";
	// 正常操作
	public final static ResponseValue OK =  new ResponseValue("200", "Access Success");
	// 用户名不存在或密码错误
	public final static ResponseValue USERERROR = new ResponseValue("201", "User Is Not Existed Or Password Error");
	// 用户名已存在
	public final static ResponseValue USEREXIST = new ResponseValue("202", "User Is Already Existed");
	// 错误
	public final static ResponseValue ERROR = new ResponseValue("404", "Error Happened");
		
	private String respMsg;
	
	private String respCode;
	
	public ResponseValue(String respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
	}
	
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	public String getRespMsg() {
		return respMsg;
	}
	
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	public String getRespCode() {
		return respCode;
	}
}
