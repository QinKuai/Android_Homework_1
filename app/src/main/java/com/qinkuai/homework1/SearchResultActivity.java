package com.qinkuai.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qinkuai.homework1.model.Course;
import com.qinkuai.homework1.model.Teacher;
import com.qinkuai.homework1.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private List<Course> coursesList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle extras = getIntent().getExtras();
        String searchString = (String) extras.get("searchString");
        SearchView searchView = findViewById(R.id.searchView_SearchResult);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = findViewById(id);
        editText.setText(searchString);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_SearchResult);
        //保持固定大小
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(layoutManager);

        initData();
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<CourseViewHolder>() {
            @NonNull
            @Override
            public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(SearchResultActivity.this).inflate(R.layout.course_list_item, null);

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
    }

    private void initData(){
        String[] coursesName = {"Java入门只需20天","Android从入门到入土","Spring全家桶","MySQL必知必会"};
        String[] coursesDesc = {"Java树常青","不来试试热门的移动开发吗","全家桶一网打尽","数据库了解一下"};
        int[] imageIds = {R.drawable.ic_subscribe_black_24dp, R.drawable.ic_subscribe_black_24dp,R.drawable.ic_subscribe_black_24dp,R.drawable.ic_subscribe_black_24dp};
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
                    Intent intent = new Intent(SearchResultActivity.this, CourseDetailActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
