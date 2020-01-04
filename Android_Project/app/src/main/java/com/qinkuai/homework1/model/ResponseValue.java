package com.qinkuai.homework1.model;

import androidx.annotation.Nullable;

import java.util.Objects;

public class ResponseValue {
    public final static String OK = "200";
    public final static String USERERROR = "201";
    public final static String USEREXIST = "202";
    public final static String ERROR = "404";

    private String respMsg;

    private String respCode;

    public ResponseValue(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseValue that = (ResponseValue) o;
        return Objects.equals(respMsg, that.respMsg) &&
                Objects.equals(respCode, that.respCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(respMsg, respCode);
    }
}
