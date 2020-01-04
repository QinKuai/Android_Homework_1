package com.qinkuai.homework1.ui.home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qinkuai.homework1.CourseAdapter;
import com.qinkuai.homework1.CourseDetailActivity;
import com.qinkuai.homework1.R;
import com.qinkuai.homework1.SearchActivity;
import com.qinkuai.homework1.model.Course;
import com.qinkuai.homework1.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RequestQueue requestQueue;

    private List<Course> coursesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        requestQueue = Volley.newRequestQueue(getActivity());

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView = root.findViewById(R.id.searchView);

        //设置搜索点击监听器
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        };
        //点击搜索栏进入搜索界面
        searchView.setOnClickListener(clickListener);
        //点击文本框同样进入搜索界面
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = root.findViewById(id);
        editText.setOnClickListener(clickListener);

        initData(root);

        recyclerView = root.findViewById(R.id.recyclerView_Home);
        //保持固定大小
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        if (!NotificationManagerCompat.from(root.getContext()).areNotificationsEnabled()) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(root.getContext())
                    .title("请手动将通知打开")
                    .positiveText("确定")
                    .negativeText("取消");
                    builder.onAny(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.NEUTRAL) {
                    } else if (which == DialogAction.POSITIVE) {
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <  Build.VERSION_CODES.O) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                            intent.putExtra("app_package", getActivity().getPackageName());
                            intent.putExtra("app_uid", getActivity().getApplicationInfo().uid);
                            startActivity(intent);
                        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                            startActivity(intent);
                        } else {
                            Intent localIntent = new Intent();
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                            startActivity(localIntent);
                        }
                    } else if (which == DialogAction.NEGATIVE) {
                    }
                }
            }).show();
        }




        return root;
    }


    private void initData(View root){

        String url = getString(R.string.serverip) + "/elearn/courses";


        Teacher teacher = new Teacher();

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray jsonArray = JSONArray.parseArray(response);
                    System.out.println(jsonArray.toJSONString());
                    int size =jsonArray.size();

                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String coursename = jsonObject.getString("name");


                        String coursedesc = jsonObject.getString("description");


                        String courdeid=jsonObject.getString("id");
                        String coursetime=jsonObject.getString("openDate");
                        String[]  strs=coursetime.split("T");
                        String startdate=strs[0];



                        String urlphoto= getString(R.string.serverip) + "/elearn/courses/";
                        urlphoto = urlphoto+courdeid+"/photo";
                        boolean end = false;
                        ImageRequest imageRequest=new ImageRequest(urlphoto, new Response.Listener<Bitmap>() {

                            @Override
                            public void onResponse(Bitmap response) {
                                if(coursesList.size()==0)
                                {
                                    coursesList.add(new Course(courdeid,response, coursename, coursedesc, teacher, 1,startdate));

                                }
                                else if(coursesList.size()==1)
                                {
                                    coursesList.add(new Course(courdeid,response, coursename, coursedesc, teacher, 2,startdate));
                                }
                                else
                                {
                                    coursesList.add(new Course(courdeid,response, coursename, coursedesc, teacher, 0,startdate));
                                }



                                if (coursesList.size() == size){

                                    mAdapter = new CourseAdapter(coursesList);
                                    recyclerView.setAdapter(mAdapter);
                                    mAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClicked(View view, int position) {
                                            CourseAdapter adapter = (CourseAdapter) recyclerView.getAdapter();
                                            String courseID = adapter.getmCourseList().get(position).getId();
                                            //System.out.println(courseID);
                                            Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                                            intent.putExtra("couseid",courseID);

                                            startActivity(intent);

                                        }
                                    });
                                }
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("ERROR");
                            }
                        });
                        requestQueue.add(imageRequest);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });

            requestQueue.add(request);


        }

    @Override    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_myself);            }        });    }


    }

