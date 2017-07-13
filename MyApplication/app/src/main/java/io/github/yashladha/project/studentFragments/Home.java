package io.github.yashladha.project.studentFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.yashladha.project.R;
import io.github.yashladha.project.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	private FirebaseUser curUser;
	private FirebaseDatabase database;

	private String TAG = getClass().getSimpleName();
	private TextView display;
	private CalendarView mCalendar;

	public Home() {
		// Required empty public constructor
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
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		Log.d(TAG, "Home fragment loaded, Student Portal");

		mAuth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();

		display = (TextView) view.findViewById(R.id.tvStudentEmail);
		mCalendar = (CalendarView) view.findViewById(R.id.cv_home_calendar);

		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				curUser = user;

				DatabaseReference userLoc = database.getReference()
						                            .child(curUser.getUid() + "/UserInfo");
				userLoc.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						User tempUser = dataSnapshot.getValue(User.class);
						display.setText(tempUser.getEmail());
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
			}
		};

		return view;
	}

}
