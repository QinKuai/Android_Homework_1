package com.qinkuai.homework1.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qinkuai.homework1.CourseAdapter;
import com.qinkuai.homework1.R;
import com.qinkuai.homework1.model.Course;

public class TypeTwoViewHolder extends TypeAbstractViewHolder {

    TextView courseNameTv2;

    ImageView headerIv2;

    public TypeTwoViewHolder(View itemView, final CourseAdapter.OnItemClickListener onClickListener) {
        super(itemView);
        this.courseNameTv2 = itemView.findViewById(R.id.type_two_coursename);

        this.headerIv2 = itemView.findViewById(R.id.type_two_courseimage);


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
        courseNameTv2.setText(course.getCourseName());
        headerIv2.setImageBitmap(course.getImageBitmap());
    }
}
