package io.github.yashladha.project.studentFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import io.github.yashladha.project.Adapter.StudentCourseAdapter;
import io.github.yashladha.project.Models.CourseModel;
import io.github.yashladha.project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Course extends Fragment {

	private String TAG = getClass().getSimpleName();

	private GridView courseGrid;

	public Course() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_course, container, false);
		courseGrid = (GridView) view.findViewById(R.id.grid_student_course);
		final CourseModel[] courses = new CourseModel[5];
		courses[0] = new CourseModel("Mathematics", "Maths");
		courses[1] = new CourseModel("Physics", "Science");
		courses[2] = new CourseModel("Chemistry", "Science");
		courses[3] = new CourseModel("Machine Learning", "CS");
		courses[4] = new CourseModel("C99", "CS");

		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
		GridLayoutAnimationController controller = new GridLayoutAnimationController(
				animation, .2f, .2f);
		StudentCourseAdapter courseAdapter = new StudentCourseAdapter(getContext(), courses);
		courseGrid.setLayoutAnimation(controller);
		courseGrid.setAdapter(courseAdapter);

		courseGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getContext(), courses[position].getCourseName(), Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}

}
