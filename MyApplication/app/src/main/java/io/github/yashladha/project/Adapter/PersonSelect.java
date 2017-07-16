package io.github.yashladha.project.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.yashladha.project.R;
import io.github.yashladha.project.User;

public class PersonSelect extends RecyclerView.Adapter<PersonSelect.ViewHolder> {

  List<User> users;

  public PersonSelect(List<User> users) {
    this.users = users;
  }

  @Override
  public PersonSelect.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.chat_person, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(PersonSelect.ViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ViewHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void onClick(View v) {

    }
  }
}
