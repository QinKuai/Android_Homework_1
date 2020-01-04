package com.qinkuai.homework1.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qinkuai.homework1.CourseAdapter;
import com.qinkuai.homework1.R;
import com.qinkuai.homework1.model.Course;

public class TypeThreeViewHolder extends TypeAbstractViewHolder {

    TextView three_courseName,three_courseDesc,three_coursetime;

    ImageView three_header;

    public TypeThreeViewHolder(View itemView, final CourseAdapter.OnItemClickListener onClickListener) {
        super(itemView);
        this.three_courseName = itemView.findViewById(R.id.three_courseName);
        this.three_courseDesc = itemView.findViewById(R.id.three_courseDesc);
        this.three_coursetime = itemView.findViewById(R.id.three_coursetime);


        this.three_header = itemView.findViewById(R.id.three_header);
        //this.rootView = itemView.findViewById(R.id.item_root);

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
        three_courseName.setText(course.getCourseName());
        three_courseDesc.setText(course.getCourseDesc());
        three_coursetime.setText(course.getCoursetime());
        three_header.setImageBitmap(course.getImageBitmap());
    }
}
