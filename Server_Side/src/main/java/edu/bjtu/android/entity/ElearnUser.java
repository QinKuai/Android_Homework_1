package edu.bjtu.android.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.alibaba.fastjson.annotation.JSONField;

public class ElearnUser implements Serializable {
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    @JSONField(serialize = false)
    private String password;
    
    @JSONField(name = "header")
    private String headerURL;
    
    private String type;
    
    private String openid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    
    public String getHeaderURL() {
		return headerURL;
	}
    
    public void setHeaderURL(String headerURL) {
		this.headerURL = headerURL;
	}
    
    public String getType() {
		return type;
	}
    
    public void setType(String type) {
		this.type = type;
	}
    
    public String getOpenid() {
		return openid;
	}
    
    public void setOpenid(String openid) {
		this.openid = openid;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", headerURL=").append(headerURL);
        sb.append(", type=").append(type);
        sb.append(", openid=").append(openid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}