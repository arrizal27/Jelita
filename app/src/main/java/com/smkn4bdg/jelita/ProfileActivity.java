package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    MaterialButton back, editProfil;
    MaterialCardView btnLogout;
    FirebaseAuth firebaseAuth;
    Context context;
    TextView editPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findView();
        firebaseAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        editPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditPasswordActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent welcome = new Intent(ProfileActivity.this, WelcomePageActivity.class);
                startActivity(welcome);
                finish();
            }
        });
    }

    private void findView(){
        back = findViewById(R.id.btn_back);
        editProfil = findViewById(R.id.btn_edit);
        editPw = findViewById(R.id.btn_edit_pw);
        btnLogout = findViewById(R.id.btn_logout);
    }
}