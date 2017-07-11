package io.github.yashladha.project;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

	private String TAG = getClass().getSimpleName();

	private EditText email;
	private Spinner spinner;
	private EditText password;
	private Button signUp;
	private EditText confirmPassword;

	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthStateListener;
	private FirebaseDatabase mDatabase;

	@Override
	protected void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(mAuthStateListener);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mAuth.removeAuthStateListener(mAuthStateListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		mAuth = FirebaseAuth.getInstance();
		mDatabase = FirebaseDatabase.getInstance();
		mAuthStateListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user != null) {
					Log.d(TAG, "User is present: " + user.getEmail());
				} else {
					Log.d(TAG, "User is not present: ");
				}
			}
		};

		AssetManager am = getApplicationContext().getAssets();
		Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "Junction-regular.otf"));

		email = (EditText) findViewById(R.id.etEmailSignUp);
		password = (EditText) findViewById(R.id.etPasswordSignUp);
		signUp = (Button) findViewById(R.id.btSignUp);
		confirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
		spinner = (Spinner) findViewById(R.id.spnChoice);

		email.setTypeface(typeface);
		password.setTypeface(typeface);
		confirmPassword.setTypeface(typeface);
		signUp.setTypeface(typeface);

		signUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final String email_ = email.getText().toString();
				String password1 = password.getText().toString();
				String password2 = confirmPassword.getText().toString();
				final String option_ = spinner.getSelectedItem().toString();

				if (email_.length() > 0) {
					if (password1.equals(password2)) {
						mAuth.createUserWithEmailAndPassword(email_, password1)
								.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
									@Override
									public void onSuccess(AuthResult authResult) {
										Log.d(TAG, "Signup completed");
										Toast.makeText(getApplicationContext(), "SignUp Completed",
												Toast.LENGTH_SHORT).show();
										Toast.makeText(getApplicationContext(), "User is been added to the database", Toast.LENGTH_SHORT)
												.show();
										DatabaseReference userInfo = mDatabase.getReference()
												.child(authResult.getUser().getUid() + "/UserInfo/");

										User userObj = new User(email_, authResult.getUser().getUid(), option_);
										userInfo.setValue(userObj).addOnSuccessListener(new OnSuccessListener<Void>() {
											@Override
											public void onSuccess(Void aVoid) {
												Log.d(TAG, "User is added to the database");
												finish();
											}
										})
										.addOnFailureListener(new OnFailureListener() {
											@Override
											public void onFailure(@NonNull Exception e) {
												Log.d(TAG, "User is unable to added to the database");
												clearFields();
												Toast.makeText(getApplicationContext(), "Database Error",
														Toast.LENGTH_SHORT).show();
											}
										});
									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Toast.makeText(getApplicationContext(), "SignUp failed",
												Toast.LENGTH_SHORT).show();
										clearFields();
										Toast.makeText(getApplicationContext(), "Input again",
												Toast.LENGTH_SHORT).show();
									}
								});
					}
				}
			}
		});

	}

	private void clearFields() {
		email.setText("");
		password.setText("");
		confirmPassword.setText("");
	}

	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(enterAnim, exitAnim);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
}
