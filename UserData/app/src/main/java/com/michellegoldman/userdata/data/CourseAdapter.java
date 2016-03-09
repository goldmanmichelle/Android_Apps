//Michelle Goldman
//CPMD 1215
//November 30, 2015

package com.michellegoldman.userdata.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michellegoldman.userdata.R;
import com.parse.ParseObject;

import java.util.List;


public class CourseAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mCourses;

    public CourseAdapter(Context context, List<ParseObject> courses ) {
        super(context, R.layout.course_list_item, courses);

        mContext = context;
        mCourses = courses;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.course_list_item, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.imageView.setImageResource(R.drawable.book);
            holder.courseName = (TextView) convertView.findViewById(R.id.new_course_textView);
            holder.timesTaken = (TextView) convertView.findViewById(R.id.times_taken_textView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject courseObject = mCourses.get(position);

        String courseName = courseObject.getString("courseName");
        holder.courseName.setText(courseName);

        int timesTaken = courseObject.getInt("timesTaken");
        holder.timesTaken.setText ( "" + timesTaken);

       // holder.imageView.setImageResource(R.drawable.book);


        return convertView;
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView courseName;
        TextView timesTaken;
    }
}
