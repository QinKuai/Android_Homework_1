package com.qinkuai.homework1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qinkuai.homework1.model.ResponseValue;
import com.qinkuai.homework1.tool.ToastShow;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private Tencent mTencent;
    private IUiListener qqLoginListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 测试校验用户是否已经登录
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String usernameCheck = sharedPreferences.getString("username", null);
        // 跳转到主页
        if (usernameCheck != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        Button loginButton = findViewById(R.id.login_Button_Login);
        Button registerButton = findViewById(R.id.register_Button_Login);
        final TextView usernameInput = findViewById(R.id.username_Input_Login);
        final TextView passwordInput = findViewById(R.id.password_Input_Login);

        // 发送登录请求
        View.OnClickListener loginListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                // 发送http请求以验证用户登录
                String url = getString(R.string.serverip) + "/elearn/login?username=" + username + "&password=" + password;

                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = JSONObject.parseObject(response);
                        ResponseValue responseValue = json.getObject("response", ResponseValue.class);
                        String userID = JSONPath.eval(json, "$.user.id") + "";
                        String userType = JSONPath.eval(json, "$.user.type") + "";
                        ToastShow.toastShow(LoginActivity.this, responseValue.getRespMsg());
                        if (responseValue.getRespCode().equals(ResponseValue.OK)) {
                            // 保存用户的登录状态
                            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userid", userID);
                            editor.putString("usertype", userType);
                            editor.putString("username", username);
                            editor.commit();

                            // 跳转到主页
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastShow.toastShow(LoginActivity.this, getString(R.string.networkError));
                    }
                });
                requestQueue.add(request);
            }
        };

        loginButton.setOnClickListener(loginListener);

        // 跳转注册页面
        View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        };

        registerButton.setOnClickListener(registerListener);

        // QQ 登录

        mTencent = Tencent.createInstance(getString(R.string.appid), LoginActivity.this);
        ImageView qqLogin = findViewById(R.id.qq_login);
        View.OnClickListener qqClickLoginListener = (view) -> {
            // 请求QQ登录接口
            qqLoginListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    // 获取openid及token
                    org.json.JSONObject json = (org.json.JSONObject) o;
                    String openid = json.optString("openid");
                    String token, expires_in;
                    try {
                        token = json.getString("access_token");
                        expires_in = json.getString("expires_in");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastShow.toastShow(LoginActivity.this, getString(R.string.networkError));
                        return;
                    }

                    // 获取用户头像及昵称
                    QQToken qqToken = mTencent.getQQToken();
                    mTencent.setOpenId(openid);
                    mTencent.setAccessToken(token, expires_in);
                    UserInfo userInfo = new UserInfo(LoginActivity.this, qqToken);
                    userInfo.getUserInfo(new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            org.json.JSONObject json = (org.json.JSONObject) o;
                            String nickname = json.optString("nickname");
                            String headerURL = json.optString("figureurl_qq_2");
                            //ToastShow.toastShow(LoginActivity.this, openid + " " + nickname + " " + headerURL);
                            // 访问后台
                            qqLogin(openid, nickname, headerURL);
                        }

                        @Override
                        public void onError(UiError uiError) {
                        }

                        @Override
                        public void onCancel() {
                        }
                    });
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(UiError uiError) {

                }
            };
            mTencent.login(LoginActivity.this, "all", qqLoginListener);
        };
        qqLogin.setOnClickListener(qqClickLoginListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResultData(requestCode, resultCode, data, qqLoginListener);
        if (requestCode == Constants.REQUEST_API) {
            mTencent.handleResultData(data, qqLoginListener);
        }
    }

    private void qqLogin(String openid, String nickname, String headerURL) {
        headerURL = Uri.encode(headerURL);
        String url = getString(R.string.serverip) + "/elearn/qqlogin?openid=" + openid + "&username=" + nickname + "&headerurl=" + headerURL;
        StringRequest request = new StringRequest(url, (response) -> {
            JSONObject json = JSONObject.parseObject(response);
            ResponseValue responseValue = json.getObject("response", ResponseValue.class);
            if (responseValue.getRespCode().equals(ResponseValue.OK)) {
                String userID = JSONPath.eval(json, "$.user.id") + "";
                String userType = JSONPath.eval(json, "$.user.type") + "";
                String header = Uri.decode(JSONPath.eval(json, "$.user.header") + "");
                String username = JSONPath.eval(json, "$.user.username") + "";

                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userid", userID);
                editor.putString("usertype", userType);
                editor.putString("username", username);
                editor.putString("headerurl", header);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, (error) -> {
            ToastShow.toastShow(LoginActivity.this, getString(R.string.networkError));
        });

        requestQueue.add(request);
    }

}
