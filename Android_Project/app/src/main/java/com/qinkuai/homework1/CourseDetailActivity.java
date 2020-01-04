package com.qinkuai.homework1;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qinkuai.homework1.model.CourseDetail;
import com.qinkuai.homework1.model.Teacher;
import com.qinkuai.homework1.tool.FileDownload;
import com.qinkuai.homework1.tool.ToastShow;
import com.qinkuai.homework1.viewmodel.CourseDetailViewModel;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class CourseDetailActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    TextView detail_name, detail_opendate, detail_desc, detail_price_status, detail_cer;
    ImageView detail_image;
    TextView detail_teacher, detail_teadesc, detail_phone, detail_email;
    ImageView detail_image_teacher;
    String courseid;
    String url = "";
    String urlteacherphoto = "";
    String teacherid;
    private Button pause, play, download, share;
    private SurfaceView surfaceView;
    private IjkMediaPlayer mPlayer;
    private Tencent mTencent;
    private IUiListener qqShareListener;

    private CourseDetailViewModel courseDetailViewModel;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
        courseid = intent.getStringExtra("couseid");

        // 配置View
        detail_name = findViewById(R.id.detail_name);
        detail_opendate = findViewById(R.id.deatail_opendate);
        detail_desc = findViewById(R.id.detail_desc);
        detail_price_status = findViewById(R.id.detail_price_status);
        detail_cer = findViewById(R.id.detail_cer);
        detail_image = findViewById(R.id.detail_image);
        detail_image_teacher = findViewById(R.id.detail_image_teacher);
        detail_teacher = findViewById(R.id.detail_teacher);
        detail_teadesc = findViewById(R.id.deatail_teadesc);
        detail_phone = findViewById(R.id.deatail_phone);
        detail_email = findViewById(R.id.deatail_email);
        surfaceView = findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(callback);
        pause = findViewById(R.id.pause);
        play = findViewById(R.id.play);
        download = findViewById(R.id.download);
        share = findViewById(R.id.share);
        // 配置数据Data
        initData();

        //此处判断安卓版本号是否大于或者等于Android8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_course";//设置通道的唯一ID
            String channelName = "elearn";//设置通道名
            int importance = NotificationManager.IMPORTANCE_HIGH;//设置通道优先级
            createNotificationChannel(channelId, channelName, importance);
        } else {
            sendNotification();
        }
    }


    private void createPlayer() {
        if (mPlayer == null) {
            mPlayer = new IjkMediaPlayer();
            mPlayer.setSpeed(1.0f);
            // 设置调用prepareAsync不自动播放，即调用start才开始播放
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mPlayer.setDataSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }


        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.pause();
            }
        };

        View.OnClickListener listener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.start();
            }
        };
        pause.setOnClickListener(listener1);

        play.setOnClickListener(listener3);
    }

    private void release() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            createPlayer();
            mPlayer.setDisplay(surfaceView.getHolder());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (surfaceView != null) {
                surfaceView.getHolder().removeCallback(callback);
                surfaceView = null;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }


    private void initData() {
        courseDetailViewModel = ViewModelProviders.of(this).get(CourseDetailViewModel.class);
        courseDetailViewModel.getCourseDetail().observe(this, new Observer<CourseDetail>() {
            @Override
            public void onChanged(CourseDetail courseDetail) {
                updateCourseDetailView(courseDetail);
            }
        });

        courseDetailViewModel.getTeacher().observe(this, (teacher)->{
            updateTeacherView(teacher);
        });

        url = getString(R.string.serverip) + "/elearn/courses/";
        url = url + courseid;
        CourseDetail courseDetail = new CourseDetail();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = JSONObject.parseObject(response);

                String coursename = jsonObject.getString("name");
                courseDetail.setCourseName(coursename);

                String coursedesc = jsonObject.getString("description");
                courseDetail.setCoursedesc(coursedesc);

                String price = jsonObject.getString("price");
                String status = jsonObject.getString("status");
                String price_status = "价格:" + price + "     课程状态:" + status;
                courseDetail.setPrice_status(price_status);

                String coursetime = jsonObject.getString("openDate");
                String[] strs = coursetime.split("T");
                String opendate = strs[0];
                courseDetail.setOpendate(opendate);

                String certification = jsonObject.getString("certification");
                String certificationDuration = jsonObject.getString("certificationDuration");
                String cer = "所发学校:" + certification + "     有效日期:" + certificationDuration;
                courseDetail.setCer(cer);
                // 添加数据项
                courseDetailViewModel.setCourseDetail(courseDetail);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR");
            }
        });

        requestQueue.add(request);


        String urlphoto = url + "/photo";
        ImageRequest imageRequest = new ImageRequest(urlphoto, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap response) {
                detail_image.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR");
            }
        });
        requestQueue.add(imageRequest);

        String urlteacher = url + "/teachers";
        StringRequest teacherRequest = new StringRequest(urlteacher, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                JSONArray jsonArray = JSONArray.parseArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);


                String teachername = jsonObject.getString("name");
                String teacherdesc = jsonObject.getString("description");
                String teacherphone = jsonObject.getString("telephone");
                String teacheremail = jsonObject.getString("email");
                teacherid = jsonObject.getString("userid");
                Teacher teacher = new Teacher(teachername, teacherphone, teacheremail, teacherdesc);
                // 配置数据项
                courseDetailViewModel.setCourseDetail(teacher);


                urlteacherphoto = getString(R.string.serverip) + "/elearn/teachers/" + teacherid;
                urlteacherphoto = urlteacherphoto + "/photo";

                ImageRequest teacherimageRequest = new ImageRequest(urlteacherphoto, new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {
                        detail_image_teacher.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR");
                    }
                });
                requestQueue.add(teacherimageRequest);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR");
            }
        });
        requestQueue.add(teacherRequest);

        // 下载
        View.OnClickListener downloadListener = (view -> {
            String url = getString(R.string.serverip) + "/elearn/materials/3/file";
            FileDownload.download(url, getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), courseid + ".pdf");
        });
        download.setOnClickListener(downloadListener);

        // 分享
        mTencent = Tencent.createInstance(getString(R.string.appid), CourseDetailActivity.this);
        View.OnClickListener shareListener = (view -> {
            // 获取到分享按钮
            qqShareListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    ToastShow.toastShow(CourseDetailActivity.this, "分享成功");
                }

                @Override
                public void onError(UiError uiError) {
                    ToastShow.toastShow(CourseDetailActivity.this, "分享失败");
                }

                @Override
                public void onCancel() {
                    ToastShow.toastShow(CourseDetailActivity.this, "取消分享");
                }
            };

            Bundle qqParams = new Bundle();
            qqParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            qqParams.putString(QQShare.SHARE_TO_QQ_TITLE, "快乐分享");
            qqParams.putString(QQShare.SHARE_TO_QQ_SUMMARY, "分享快乐");
            qqParams.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://space.bilibili.com/13261253");
            qqParams.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
            mTencent.shareToQQ(this, qqParams, qqShareListener);
        });
        share.setOnClickListener(shareListener);
    }

    private void updateCourseDetailView(CourseDetail courseDetail){
        detail_name.setText(courseDetail.getCourseName());
        detail_desc.setText(courseDetail.getCoursedesc());

        detail_price_status.setText(courseDetail.getPrice_status());
        detail_opendate.setText(courseDetail.getOpendate());
        detail_cer.setText(courseDetail.getCer());


    }

    private void updateTeacherView(Teacher teacher){
        detail_teacher.setText(teacher.getName());
        detail_teadesc.setText(teacher.getTeacher_desc());
        detail_phone.setText(teacher.getPhone());
        detail_email.setText(teacher.getEmail());
    }

    private void sendNotification(){
        // 通知用户某一节课的信息
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);
        Intent mintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://space.bilibili.com/13261253"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mintent, 0);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.ic_discount);
        builder.setContentTitle(getString(R.string.notification_title));
        builder.setAutoCancel(true);
        builder.setContentText(getString(R.string.notification_content));
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notificationManager.notify(2, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance){
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(this);
        Intent mintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://space.bilibili.com/13261253"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mintent, 0);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.ic_discount);
        builder.setContentTitle(getString(R.string.notification_title));
        builder.setAutoCancel(true);
        builder.setChannelId(channelId);
        builder.setContentText(getString(R.string.notification_content));
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notificationManager.notify(2, notification);
    }
}

