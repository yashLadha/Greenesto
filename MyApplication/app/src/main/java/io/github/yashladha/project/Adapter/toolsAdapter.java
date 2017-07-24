package io.github.yashladha.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import io.github.yashladha.project.R;


public class toolsAdapter extends BaseAdapter {

  private Context mContext;
  private ArrayList<Integer> list;

  public toolsAdapter(Context context, ArrayList<Integer> imageId) {
    this.list = imageId;
    this.mContext = context;
  }

  @Override
  public int getCount() {
    return list.size();
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
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.tools_index, parent, false);
    ImageView image = (ImageView) view.findViewById(R.id.iv_tools_index);
    image.setImageResource(list.get(position));
    return view;
  }
}
