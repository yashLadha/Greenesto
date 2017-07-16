package io.github.yashladha.project.studentFragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.PopupWindow;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.yashladha.project.Adapter.PersonsAdapter;
import io.github.yashladha.project.R;
import io.github.yashladha.project.User;

public class ChatStudent extends Fragment {

  private String TAG = getClass().getSimpleName();

  private FloatingActionButton addPerson;

  public ChatStudent() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "Chat Student Fragment initiated");
    View view = inflater.inflate(R.layout.fragment_chat_student, container, false);
    addPerson = (FloatingActionButton) view.findViewById(R.id.fab_add_person);

    addPerson.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        initiatePopup(v);
      }
    });
    return view;
  }

  private void initiatePopup(View v) {
    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE
    );
    View inflatedView = layoutInflater.inflate(R.layout.person_popup, null, false);
    RecyclerView personList = (RecyclerView) inflatedView.findViewById(R.id.rv_add_person);

    List<User> users = new ArrayList<>();
    PersonsAdapter persons = new PersonsAdapter(users);
    users = getUserList(persons, users);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
    personList.setLayoutManager(mLayoutManager);
    personList.setAdapter(persons);

    Display display = getActivity().getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);

    PopupWindow popUpWindow = new PopupWindow(inflatedView, size.x-50, size.y-400, true);
    popUpWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.course_rounded));
    popUpWindow.setFocusable(true);
    popUpWindow.setOutsideTouchable(true);
    popUpWindow.setAnimationStyle(R.style.anim_popup);
    popUpWindow.showAtLocation(v, Gravity.BOTTOM, 0, 100);
  }

  public List<User> getUserList(final PersonsAdapter persons, final List<User> users) {
    DatabaseReference userData = FirebaseDatabase.getInstance().getReference();
    userData.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
          for (DataSnapshot userInfo : data.getChildren()) {
            Log.d(TAG, userInfo.toString());
            User temp = userInfo.getValue(User.class);
            users.add(temp);
          }
        }
        persons.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
      }
    });
    return users;
  }
}
