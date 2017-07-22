package io.github.yashladha.project.studentFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.yashladha.project.R;

public class Storage extends Fragment {

  private String TAG = getClass().getSimpleName();
  private RecyclerView storageList;

  public Storage() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_storage, container, false);
    storageList = (RecyclerView) view.findViewById(R.id.rv_storage_list);
    RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(), 3);
    storageList.setLayoutManager(lm);
    return view;
  }

}
