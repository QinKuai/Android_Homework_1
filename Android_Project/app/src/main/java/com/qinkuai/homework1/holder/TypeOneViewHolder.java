package com.qinkuai.homework1.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qinkuai.homework1.CourseAdapter;
import com.qinkuai.homework1.R;
import com.qinkuai.homework1.model.Course;

public class TypeOneViewHolder extends TypeAbstractViewHolder {
    View rootView;
    TextView courseNameTv;
    TextView courseDescTv;
    ImageView headerIv;

    public TypeOneViewHolder(View itemView, final CourseAdapter.OnItemClickListener onClickListener) {
        super(itemView);
        this.courseNameTv = itemView.findViewById(R.id.detail_teacher_name);
        this.courseDescTv = itemView.findViewById(R.id.detail_teacher_phone);
        this.headerIv = itemView.findViewById(R.id.detail_teacher);
        this.rootView = itemView.findViewById(R.id.item_root);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    int position = getAdapterPosition();
                    //确保position值有效
                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.onItemClicked(view, position);
                    }
                }
            }
        });

    }


    @Override
    public void bindHolder(Course course) {
        courseNameTv.setText(course.getCourseName());
        courseDescTv.setText(course.getCourseDesc());


        headerIv.setImageBitmap(course.getImageBitmap());

    }
}
