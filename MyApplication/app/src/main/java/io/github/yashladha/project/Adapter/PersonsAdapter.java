package io.github.yashladha.project.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.yashladha.project.R;
import io.github.yashladha.project.User;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.ViewHolder>{

  private String TAG = getClass().getSimpleName();
  private List<User> users;

  public PersonsAdapter(List<User> users) {
    this.users = users;
  }

  @Override
  public PersonsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.person_index, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(PersonsAdapter.ViewHolder holder, int position) {
    Log.d(TAG, position + " Inflated");
    User temp = users.get(position);
    holder.email.setText(temp.getEmail());
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView email;
    ImageView profileImage;

    public ViewHolder(View itemView) {
      super(itemView);
      email = (TextView) itemView.findViewById(R.id.tv_person_email);
      profileImage = (ImageView) itemView.findViewById(R.id.iv_person_image);
    }
  }
}
