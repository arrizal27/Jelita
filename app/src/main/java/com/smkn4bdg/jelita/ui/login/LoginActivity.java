package com.smkn4bdg.jelita.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.ui.main.MainActivity;
import com.smkn4bdg.jelita.ui.WelcomePageActivity;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbUsers;
    private static final String TAG = "LoginActivity";
    User user = new User();
    EditText username;
    TextInputEditText pass;
    Button btnLogin;
    ImageButton googlelogin, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();

        //auto login process
        //move to main activity if user already sign in
        if (firebaseAuth.getCurrentUser() != null) {
            // User is logged in
            System.out.println("Email : " +firebaseAuth.getCurrentUser().getEmail());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.login();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(LoginActivity.this, WelcomePageActivity.class);
                startActivity(back);
                finish();
            }
        });

        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.googleSign();
            }
        });

    }

    private void findView(){
        username = findViewById(R.id.login_username);
        pass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_masuk);
        back = findViewById(R.id.back_login);
        googlelogin = findViewById(R.id.googlesignin);
        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void login(){
        String usernameFinal = username.getText().toString();
        final String passFinal = pass.getText().toString();

        if (TextUtils.isEmpty(usernameFinal)) {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passFinal)) {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //login user
        firebaseAuth.signInWithEmailAndPassword(usernameFinal, passFinal)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {

                            if (passFinal.length() < 6) {
                                pass.setError(LoginActivity.this.getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                            user.setEmail(usernameFinal);
                            user.setPassword(passFinal);
                        }
                    }
                });

    }

    private void googleSign(){

    }
}