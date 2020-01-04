package com.qinkuai.homework1.ui.myself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.qinkuai.homework1.LoginActivity;
import com.qinkuai.homework1.R;
import com.qinkuai.homework1.tool.ToastShow;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MyselfFragment extends Fragment {
    private View root;
    private RequestQueue requestQueue;
    private Tencent mTencent;
    private IUiListener qqShareListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        requestQueue = Volley.newRequestQueue(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String userID = sharedPreferences.getString("userid", null);
        String type = sharedPreferences.getString("usertype", null);

        // 获取在本地存储的用户信息
        root = inflater.inflate(R.layout.fragment_myself_login, container, false);
        ImageView headerView = root.findViewById(R.id.header_Myself_Login);
        // 设置用户名
        TextView textView = root.findViewById(R.id.usernameMyselfLogin);
        textView.setText(username);
        // 设置用户头像
        String headerURL = (type.equals("n")) ? getString(R.string.serverip) + "/elearn/user/" + userID + "/header"
                    : sharedPreferences.getString("headerurl", null);
        ImageRequest headerRequest = new ImageRequest(headerURL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                headerView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastShow.toastShow(getContext(),getString(R.string.networkError));
            }
        });

        requestQueue.add(headerRequest);

        Button acountManage = root.findViewById(R.id.buttonMyselfLogin);
        acountManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userid", null);
                editor.putString("username", null);
                editor.putString("usertype", null);
                if (type.equals("q")){
                    editor.putString("headerurl", null);
                }
                editor.commit();
                ToastShow.toastShow(getContext(), getString(R.string.exitLogin));

                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
    }
}