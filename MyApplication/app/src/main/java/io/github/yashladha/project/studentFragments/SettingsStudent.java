package io.github.yashladha.project.studentFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.yashladha.project.R;


public class SettingsStudent extends Fragment {

  private String TAG = getClass().getSimpleName();

  private EditText updateEmail;
  private EditText updatePassword;
  private RadioButton updateEmailButton;
  private RadioButton updatePasswordButton;
  private Button updateProfile;

  public SettingsStudent() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.i(TAG, "Settings Fragment student inflated");
    View view = inflater.inflate(R.layout.fragment_settings_student, container, false);
    updateEmail = (EditText) view.findViewById(R.id.et_email_changed);
    updatePassword = (EditText) view.findViewById(R.id.et_password_changed);
    updateEmailButton = (RadioButton) view.findViewById(R.id.rb_email_change);
    updatePasswordButton = (RadioButton) view.findViewById(R.id.rb_password_change);
    updateProfile = (Button) view.findViewById(R.id.bt_update_profile);

    updateEmail.setEnabled(false);
    updatePassword.setEnabled(false);

    updateEmailButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateEmail.setEnabled(true);
      }
    });
    updatePasswordButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updatePassword.setEnabled(true);
      }
    });
    updateProfile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final String emailUpdated_ = updateEmail.getText().toString();
        String passwordUpdated_ = updatePassword.getText().toString();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (emailUpdated_.length() > 0) {
          if (user != null) {
            user.updateEmail(emailUpdated_)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                      updateEmailUserDB(user, emailUpdated_);
                      Log.d(TAG, "Email Updated Successfully");
                    } else {
                      Log.e(TAG, "Email is not updated");
                    }
                  }
                });
          }
        }

        if (passwordUpdated_.length() > 0) {
          if (user != null) {
            user.updatePassword(passwordUpdated_)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                      Log.d(TAG, "Password updated successfully");
                    } else {
                      Log.e(TAG, "Password is not updated");
                    }
                  }
                });
          }
        }
      }
    });

    return view;
  }

  private void updateEmailUserDB(FirebaseUser user, String emailUpdated_) {
    DatabaseReference userDB = FirebaseDatabase.getInstance()
        .getReference()
        .child(user.getUid())
        .child("UserInfo")
        .child("email");
    userDB.setValue(emailUpdated_).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          Log.d(TAG, "Email updated successfully in DB");
        } else {
          Log.e(TAG, "Email not updated Successfully in DB");
        }
      }
    });
  }

}
