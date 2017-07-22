package io.github.yashladha.project.studentFragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.yashladha.project.Adapter.StudentCourseAdapter;
import io.github.yashladha.project.Models.CourseModel;
import io.github.yashladha.project.R;


public class Course extends Fragment {

  public static final DatabaseReference DATABASE_REFERENCE = FirebaseDatabase.getInstance()
      .getReference();
  public static final FirebaseUser CURRENT_USER = FirebaseAuth.getInstance().getCurrentUser();
  private String TAG = getClass().getSimpleName();

  private GridView courseGrid;
  private StudentCourseAdapter courseAdapter;
  private Spinner courseOptionSpinner;
  private static final int STAGES = 2;
  private int choicesClicked = 0;

  public Course() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_course, container, false);
    courseGrid = (GridView) view.findViewById(R.id.grid_student_course);

    final List<CourseModel> courses = new ArrayList<>();

    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
    courseOptionSpinner = (Spinner) view.findViewById(R.id.sp_course_options);
    GridLayoutAnimationController controller = new GridLayoutAnimationController(
        animation, .2f, .2f);
    courseAdapter = new StudentCourseAdapter(getContext(), courses);
    courseGrid.setLayoutAnimation(controller);
    courseGrid.setAdapter(courseAdapter);

    courseAsyncInflater logic = new courseAsyncInflater();
    try {
      logic.execute(courseAdapter);
    } catch (NullPointerException e) {
      Log.e(TAG, "courseAdapter is null");
    }

    return view;
  }

  private class courseAsyncInflater extends AsyncTask<StudentCourseAdapter, Void, Void> {

    @Override
    protected Void doInBackground(StudentCourseAdapter... params) {
      StudentCourseAdapter adapter = params[0];
      if (adapter != null) {
        Log.d(TAG, "Logic for course initiated");
        logicCourse(choicesClicked);
      }
      return null;
    }

    protected void inflateGrid() {
      Log.d(TAG, "Inflate the grid view");
      ArrayList<CourseModel> courses = new ArrayList<>();
      courses.add(new CourseModel("C++", "CS"));
      courses.add(new CourseModel("Calculus", "Mathematics"));
      courseAdapter = new StudentCourseAdapter(getContext(), courses);
      courseGrid.setAdapter(courseAdapter);
      courseAdapter.notifyDataSetChanged();
    }

    private void logicCourse(int i) {
      switch (i) {
        case 0:
          final List<String> genre = Arrays.asList(getResources()
              .getStringArray(R.array.genre));
          ArrayAdapter<String> genreAdapter = getStringArrayAdapter(genre);
          try {
            courseOptionSpinner.setAdapter(genreAdapter);
            courseOptionSpinner.setSelection(0, false);
            courseOptionSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> parent,
                                             View view, int position, long id) {
                    choicesClicked++;
                    Log.d("Genre selected: ", genre.get(position));
                    courseOptionSpinner.setClickable(false);
                    inflateDatabase(position, genre, "genre");
                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> parent) {
                    Log.d(TAG, "No Item is selected");
                  }
                }
            );
          } catch (Exception e) {
            Log.e(TAG, "Error Occured in Thread");
          }
          break;
        case 1:
          final List<String> level = Arrays.asList(getResources().getStringArray(R.array.level));
          ArrayAdapter<String> levelAdapter = getStringArrayAdapter(level);
          courseOptionSpinner.setAdapter(levelAdapter);
          courseOptionSpinner.setSelection(0, false);
          courseOptionSpinner.setOnItemSelectedListener(

              new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  choicesClicked++;
                  Log.d("Level selected: ", level.get(position));
                  courseOptionSpinner.setClickable(false);
                  inflateDatabase(position, level, "level");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                  Log.e(TAG, "No Item is selected");
                }
              }
          );
          break;
        case 2:
          inflateGrid();
          break;
      }
    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter(List<String> genre) {
      ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(
          getContext(),
          android.R.layout.simple_spinner_item,
          genre
      );
      genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      return genreAdapter;
    }

    private void inflateDatabase(int position, List<String> genre, String s) {
      DatabaseReference setGenre = DATABASE_REFERENCE
          .child(CURRENT_USER.getUid())
          .child(s);
      setGenre.setValue(genre.get(position)).addOnCompleteListener(
          new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                courseOptionSpinner.setClickable(true);
                logicCourse(choicesClicked);
              } else {
                Log.d(TAG, "Error occured in task");
              }
            }
          }
      );
    }
  }

}
