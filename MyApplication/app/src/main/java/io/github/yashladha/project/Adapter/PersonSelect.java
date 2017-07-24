package io.github.yashladha.project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import io.github.yashladha.project.R;
import io.github.yashladha.project.User;
import io.github.yashladha.project.Fragments.ChatUI;

public class PersonSelect extends RecyclerView.Adapter<PersonSelect.ViewHolder> {

  private String TAG = getClass().getSimpleName();

  private Context context;
  private List<User> users;

  public PersonSelect(Context context, List<User> users) {
    this.users = users;
    this.context = context;
  }

  @Override
  public PersonSelect.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.chat_person, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(PersonSelect.ViewHolder holder, int position) {
    User temp = users.get(position);
    holder.email.setText(temp.getEmail());
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView email;

    ViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);

      email = (TextView) itemView.findViewById(R.id.tv_email_chat);
    }

    @Override
    public void onClick(View v) {
      Log.d(TAG, String.valueOf(getAdapterPosition()) + " Clicked");
      Intent intent = new Intent(context, ChatUI.class);
      intent.putExtra("userObject", (Serializable) users.get(getAdapterPosition()));
      context.startActivity(intent);
    }
  }
}
