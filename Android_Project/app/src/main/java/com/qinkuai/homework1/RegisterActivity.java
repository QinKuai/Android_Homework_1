package com.qinkuai.homework1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class RegisterActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        Button backToLogin = findViewById(R.id.backToLoginButton);
        Button register = findViewById(R.id.registerButtonRegister);
        final TextView usernameInput = findViewById(R.id.usernameInputRegister);
        final TextView passwordInput = findViewById(R.id.passwordInputRegister);
        final TextView passwordCheckInput = findViewById(R.id.passwordCheckRegister);

        View.OnClickListener registerListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String passwordCheck = passwordCheckInput.getText().toString();

                if (!password.equals(passwordCheck)){
                    ToastShow.toastShow(RegisterActivity.this, getString(R.string.passwordCheckError));
                    return;
                }else{
                    String url = getString(R.string.serverip) + "/elearn/register?username=" + username + "&password=" + password;
                    StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject json = JSONObject.parseObject(response);
                            ResponseValue responseValue = json.getObject("response", ResponseValue.class);
                            String userID = JSONPath.eval(json, "$.user.id") + "";
                            String userType = JSONPath.eval(json, "$.user.type") + "";
                            ToastShow.toastShow(RegisterActivity.this, responseValue.getRespMsg());
                            switch (responseValue.getRespCode()){
                                case ResponseValue.OK:
                                    // 本地保存用户登录状态
                                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("userid", userID);
                                    editor.putString("usertype", userType);
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.commit();

                                    // 跳转到主页
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    break;
                                default:
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ToastShow.toastShow(RegisterActivity.this,getString(R.string.networkError));
                        }
                    });
                    requestQueue.add(request);
                }
            }
        };

        View.OnClickListener backToLoginListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };

        backToLogin.setOnClickListener(backToLoginListener);
        register.setOnClickListener(registerListener);
    }
}
