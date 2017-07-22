package io.github.yashladha.project.studentFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.HashSet;

import io.github.yashladha.project.Adapter.StorageAdapter;
import io.github.yashladha.project.R;
import io.github.yashladha.project.studentFragments.util.ThumbanilPDF;

import static android.app.Activity.RESULT_OK;

public class Storage extends Fragment {

  private String TAG = getClass().getSimpleName();
  private RecyclerView storageList;
  private StorageAdapter storageAdapter;
  private static final FirebaseUser CUR_USER = FirebaseAuth.getInstance().getCurrentUser();
  private static final StorageReference STORAGE = FirebaseStorage.getInstance().getReference()
      .child(CUR_USER.getUid());
  private static final DatabaseReference DATABASE = FirebaseDatabase.getInstance().getReference()
      .child(CUR_USER.getUid()).child("Storage");
  private FloatingActionButton uploadBtn;
  private HashSet<Uri> fileSet;
  private ThumbanilPDF thumbanilPDF;
  private static final int PDF_FIND = 1;

  public Storage() {
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PDF_FIND) {
      if (resultCode == RESULT_OK) {
        Uri uri = data.getData();
        Log.d(TAG, uri.toString());
        StorageReference fileLoc = STORAGE.child(uri.getLastPathSegment());
        UploadTask uploadTask = fileLoc.putFile(uri);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
            if (task.isSuccessful()) {
              Log.d(TAG, "Task is successful");
              DATABASE.push().setValue(task.getResult().getDownloadUrl().toString())
                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Uploaded successfully", Toast.LENGTH_SHORT)
                            .show();
                      }
                    }
                  });
            }
          }
        });
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_storage, container, false);
    fileSet = new HashSet<>();
    storageList = (RecyclerView) view.findViewById(R.id.rv_storage_list);
    RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(), 3);
    storageList.setLayoutManager(lm);
    uploadBtn = (FloatingActionButton) view.findViewById(R.id.fab_storage_upload);
    thumbanilPDF = new ThumbanilPDF(getContext());

    uploadBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PDF_FIND);
      }
    });

    DATABASE.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
          if (!fileSet.contains(Uri.parse((String) data.getValue()))) {
            Log.d(TAG, String.valueOf(Uri.parse((String) data.getValue())));
            fileSet.add(Uri.parse((String) data.getValue()));
            File file = new File("/sdcard/" + data.getKey() + ".pdf");
            if (!file.exists()) {
              Ion.with(getContext())
                  .load((String) data.getValue())
                  .write(new File("/sdcard/"+data.getKey()+".pdf"))
                  .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                      Log.d(TAG, "File download successful " + result.getName());
                    }
                  });
            } else {
              Log.d(TAG, "File is already present");
            }
          }
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, databaseError.getMessage());
      }
    });
    return view;
  }

}
