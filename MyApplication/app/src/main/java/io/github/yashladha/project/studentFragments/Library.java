package io.github.yashladha.project.studentFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import io.github.yashladha.project.R;

public class Library extends Fragment {

  private String TAG = getClass().getSimpleName();

  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private FirebaseStorage mStorage;
  private FirebaseUser user;

  public Library() {
  }

  @Override
  public void onStart() {
    super.onStart();
    mAuth.addAuthStateListener(mAuthListener);
  }

  @Override
  public void onStop() {
    super.onStop();
    mAuth.removeAuthStateListener(mAuthListener);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_library, container, false);
    mAuth = FirebaseAuth.getInstance();
    mStorage = FirebaseStorage.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        user = firebaseAuth.getCurrentUser();
      }
    };

    StorageReference lectureLocation = mStorage.getReference()
        .child("Library/Teknuance-Sample Assignment-Input_String.pdf");
    readPdfFromStorage(lectureLocation);

    return view;
  }

  private void readPdfFromStorage(StorageReference lecLocation) {
    try {
      final File localFile = new File(
          Environment.getExternalStorageDirectory().toString() + "/lecture.pdf"
      );
      lecLocation.getFile(localFile)
          .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
              if (localFile.exists()) {
                Log.d(TAG, "File is created " + localFile.getPath());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(localFile), "application/pdf");
                startActivity(intent);
              }
            }
          });
    } catch (Exception e) {
      Log.e(TAG, "Temp file not able to create");
    }
  }

}
