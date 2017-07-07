package io.github.yashladha.project;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private Button loginBtn;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AssetManager am = getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "Junction-regular.otf"));

        email = (EditText) findViewById(R.id.etLoginEmail);
        password = (EditText) findViewById(R.id.etPassword);
        loginBtn = (Button) findViewById(R.id.btLogin);

        email.setTypeface(typeface);
        password.setTypeface(typeface);
        loginBtn.setTypeface(typeface);
    }
}
