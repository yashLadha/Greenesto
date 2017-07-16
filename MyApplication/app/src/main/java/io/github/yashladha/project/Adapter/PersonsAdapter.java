package io.github.yashladha.project.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.github.yashladha.project.R;
import io.github.yashladha.project.User;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.ViewHolder> {

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

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView email;
    ImageView profileImage;

    ViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      email = (TextView) itemView.findViewById(R.id.tv_person_email);
      profileImage = (ImageView) itemView.findViewById(R.id.iv_person_image);
    }

    @Override
    public void onClick(View v) {
      User userObj = users.get(getAdapterPosition());
      FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
      if (curUser != null)
        addPersonToChat(userObj.getUid(), curUser.getUid());
    }

    private void addPersonToChat(final String personUid, String curUserUid) {
      final DatabaseReference chatEnable = FirebaseDatabase.getInstance().getReference()
          .child(curUserUid)
          .child("Chats");
      chatEnable.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          chatEnable.child(personUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists()) {
                Log.d(TAG, "User already added to chat");
              } else {
                chatEnable.child(personUid).setValue(personUid)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                          Log.d(TAG, "Person added to chat Successfully");
                        }
                      }
                    });
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
              Log.e(TAG, databaseError.getDetails());
            }
          });
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
          Log.e(TAG, databaseError.getMessage());
        }
      });
    }
  }
}
