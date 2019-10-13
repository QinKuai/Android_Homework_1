package com.qinkuai.homework1.ui.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qinkuai.homework1.CourseDetailActivity;
import com.qinkuai.homework1.R;
import com.qinkuai.homework1.SearchActivity;
import com.qinkuai.homework1.model.Course;
import com.qinkuai.homework1.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Course> coursesList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView = root.findViewById(R.id.searchView);

        //设置搜索点击监听器
        View.OnClickListener clickListener = new View.OnClickListener(){
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

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_Home);
        //保持固定大小
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(layoutManager);

        initData();
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<CourseViewHolder>() {
            @NonNull
            @Override
            public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.course_list_item, null);

                return new CourseViewHolder(view, this);
            }

            @Override
            public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
                holder.courseNameTv.setText(coursesList.get(position).getCourseName());
                holder.courseDescTv.setText(coursesList.get(position).getCourseDesc());
                holder.headerIv.setImageResource(coursesList.get(position).getImageId());
            }

            @Override
            public int getItemCount() {
                return coursesList.size();
            }
        };
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void initData(){
        String[] coursesName = {"数学速成","语文阅读班","托福80天轻松100+","Java入门只需20天","Android从入门到入土","Spring全家桶","MySQL必知必会"};
        String[] coursesDesc = {"教你10天速成微积分","阅读轻松拿高分","100+不是轻松的很","Java树常青","不来试试热门的移动开发吗","全家桶一网打尽","数据库了解一下"};
        int[] imageIds = {R.drawable.ic_home_black_24dp,R.drawable.ic_subscribe_black_24dp,R.drawable.ic_subscribe_black_24dp, R.drawable.ic_myself_black_24dp,
                R.drawable.ic_subscribe_black_24dp,R.drawable.ic_subscribe_black_24dp,R.drawable.ic_subscribe_black_24dp};
        Teacher teacher = new Teacher("QinKuai","QinKuai");

        for (int i = 0; i < coursesName.length; i++){
            this.coursesList.add(new Course(imageIds[i], coursesName[i], coursesDesc[i], teacher));
        }
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{
        View rootView;
        TextView courseNameTv;
        TextView courseDescTv;
        ImageView headerIv;
        private RecyclerView.Adapter adapter;

        public CourseViewHolder(View itemView, RecyclerView.Adapter adapter){
            super(itemView);
            this.courseNameTv = itemView.findViewById(R.id.courseName);
            this.courseDescTv = itemView.findViewById(R.id.courseDesc);
            this.headerIv = itemView.findViewById(R.id.header);
            this.rootView = itemView.findViewById(R.id.item_root);
            addOnclickListener(this.rootView);
            this.adapter = adapter;
        }

        private void addOnclickListener(View item){
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}