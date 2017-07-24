package io.github.yashladha.project;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

	private String TAG = getClass().getSimpleName();

	private EditText email;
	private Button loginBtn;
	private Button signUpBtn;
	private EditText password;

	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthStateListener;

	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}

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
		setContentView(R.layout.activity_login);

		AssetManager am = getApplicationContext().getAssets();
		Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "Junction-regular.otf"));

		email = (EditText) findViewById(R.id.etLoginEmail);
		password = (EditText) findViewById(R.id.etPasswordSignUp);
		loginBtn = (Button) findViewById(R.id.btLogin);
		signUpBtn = (Button) findViewById(R.id.btSignup);

		mAuth = FirebaseAuth.getInstance();
		mAuthStateListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user != null) {
					Log.d(TAG, "User is logged in before");
				} else {
					Log.d(TAG, "No user is logged in before");
				}
			}
		};

		email.setTypeface(typeface);
		signUpBtn.setTypeface(typeface);
		password.setTypeface(typeface);
		loginBtn.setTypeface(typeface);

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String email_ = email.getText().toString();
				String password_ = password.getText().toString();

				if (email_.length() > 0) {
					mAuth.signInWithEmailAndPassword(email_, password_).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
						@Override
						public void onSuccess(AuthResult authResult) {
							Log.d(TAG, "User is signed in " + authResult.getUser().getEmail());
							//TODO: Needs to be done based on the type of login
							Intent intent = new Intent(getApplicationContext(), Portal.class);
							startActivity(intent);
							finish();
						}
					}).addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {
							Log.d(TAG, "User is unable to signin");
							Toast.makeText(getApplicationContext(), "User is unable to sign in", Toast.LENGTH_SHORT).show();
							clearFields();
						}
					});
				}
			}
		});

		signUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), SignUp.class);
				startActivity(i);
				LoginActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}

	private void clearFields() {
		email.setText("");
		password.setText("");
	}
}
