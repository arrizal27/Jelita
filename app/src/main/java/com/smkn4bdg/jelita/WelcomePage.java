package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.smkn4bdg.jelita.daftar.DaftarActivity;

public class WelcomePage extends AppCompatActivity {
FirebaseAuth firebaseAuth;
Button btnLogin;
Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        findView();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            // User is logged in
            System.out.println("Email : " +firebaseAuth.getCurrentUser().getEmail());
            btnLogin.setText("Masuk");
            btnDaftar.setEnabled(false);
            btnDaftar.setVisibility(View.INVISIBLE);
        }

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regis = new Intent(WelcomePage.this, DaftarActivity.class);
                startActivity(regis);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(WelcomePage.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    private void findView(){
        btnLogin = findViewById(R.id.btnlogin);
        btnDaftar = findViewById(R.id.btndaftar);
    }

}