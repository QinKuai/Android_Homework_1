package com.qinkuai.homework1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qinkuai.homework1.holder.TypeAbstractViewHolder;
import com.qinkuai.homework1.holder.TypeOneViewHolder;
import com.qinkuai.homework1.holder.TypeThreeViewHolder;
import com.qinkuai.homework1.holder.TypeTwoViewHolder;
import com.qinkuai.homework1.model.Course;

import java.util.List;

import static com.qinkuai.homework1.model.Course.TYPE_ONE;
import static com.qinkuai.homework1.model.Course.TYPE_THREE;
import static com.qinkuai.homework1.model.Course.TYPE_TWO;

public class CourseAdapter extends RecyclerView.Adapter {

    private List<Course> mCourseList;
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public List<Course> getmCourseList() {
        return mCourseList;
    }

    public CourseAdapter(List<Course> CourseList)
    {
        mCourseList= CourseList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_ONE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_one, null);
                return new TypeOneViewHolder(view, mOnItemClickListener);
            }
            case TYPE_TWO: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_two, null);
                return new TypeTwoViewHolder(view, mOnItemClickListener);
            }
            case TYPE_THREE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_three, null);
                return new TypeThreeViewHolder(view, mOnItemClickListener);
            }

        }
          return null;

        //CourseViewHolder viewHolder = new  CourseViewHolder(view, mOnItemClickListener);


    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((TypeAbstractViewHolder)holder).bindHolder(mCourseList.get(position));

    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Course moreTypeBean = mCourseList.get(position);
        return moreTypeBean.type;



    }


}
