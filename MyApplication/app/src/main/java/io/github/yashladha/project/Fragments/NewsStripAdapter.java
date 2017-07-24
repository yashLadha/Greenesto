package io.github.yashladha.project.Fragments;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yashladha.project.R;

class NewsStripAdapter extends PagerAdapter {

  private Context mContext;
  private ArrayList<String> News;

  public NewsStripAdapter(Context context, ArrayList<String> news) {
    this.mContext = context;
    this.News = news;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.news, container, false);
    TextView strip = (TextView) layout.findViewById(R.id.tv_news_strip);
    strip.setText(News.get(position));
    container.addView(layout);
    return layout;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getCount() {
    return News.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }
}
