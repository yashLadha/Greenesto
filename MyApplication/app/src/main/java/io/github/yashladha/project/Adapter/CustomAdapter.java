package io.github.yashladha.project.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yashladha.project.Models.Quiz;
import io.github.yashladha.project.R;
import io.github.yashladha.project.studentFragments.util.utilPlayLecture;

public class CustomAdapter extends PagerAdapter {

  private String TAG = getClass().getSimpleName();
  private Context mConetxt;
  private ArrayList<Quiz> questions;

  public CustomAdapter(Context context, ArrayList<Quiz> questions) {
    this.mConetxt = context;
    this.questions = questions;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    Log.d(TAG, String.valueOf(position));
    LayoutInflater inflater = LayoutInflater.from(mConetxt);
    ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.quiz, container, false);
    setQuiz(layout, position);
    container.addView(layout);
    return layout;
  }

  private void setQuiz(ViewGroup layout, int position) {
    TextView question = (TextView) layout.findViewById(R.id.tv_question_heading);
    TextView option[] = new TextView[4];
    option[0] = (TextView) layout.findViewById(R.id.tv_option_1);
    option[1] = (TextView) layout.findViewById(R.id.tv_option_2);
    option[2] = (TextView) layout.findViewById(R.id.tv_option_3);
    option[3] = (TextView) layout.findViewById(R.id.tv_option_4);

    question.setText("Q: " + (position+1) + " " + questions.get(position).getQuestion());
    for (int i = 0; i < 4; i++) {
      option[i].setText(questions.get(position).getOption()[i]);
    }
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getCount() {
    return questions.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }
}
