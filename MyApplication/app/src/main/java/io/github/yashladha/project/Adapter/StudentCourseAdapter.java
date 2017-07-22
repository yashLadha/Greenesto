package io.github.yashladha.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.github.yashladha.project.Models.CourseModel;
import io.github.yashladha.project.R;

public class StudentCourseAdapter extends BaseAdapter {

	private Context context;
	private List<CourseModel> courses;

	public StudentCourseAdapter(Context context, List<CourseModel> courses) {
		this.context = context;
		this.courses = courses;
	}

	@Override
	public int getCount() {
		return courses.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		View gridView;
		if (convertView == null) {
			gridView = new View(context);
			gridView = inflater.inflate(R.layout.course_index, null);
			TextView courseLabel = (TextView) gridView.findViewById(R.id.tv_course_label);
			CourseModel tempCourse = courses.get(position);
			courseLabel.setText(tempCourse.getCourseName());
		} else {
			gridView = convertView;
		}
		return gridView;
	}
}
