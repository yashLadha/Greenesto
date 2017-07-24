package io.github.yashladha.project.studentFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import io.github.yashladha.project.Adapter.toolsAdapter;
import io.github.yashladha.project.R;

public class Tools extends Fragment {

  private GridView gridView;
  private toolsAdapter toolsAdap;
  private ArrayList<Integer> imageId;

  public Tools() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_tools, container, false);

    imageId = new ArrayList<>();
    imageId.add(R.drawable.translate);
    imageId.add(R.drawable.paint);
    imageId.add(R.drawable.calc);
    imageId.add(R.drawable.one_note);

    gridView = (GridView) view.findViewById(R.id.gv_tools);
    toolsAdap = new toolsAdapter(getContext(), imageId);
    gridView.setAdapter(toolsAdap);

    return view;
  }

}
