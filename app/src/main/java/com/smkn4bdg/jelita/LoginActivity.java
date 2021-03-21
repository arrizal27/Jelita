package com.smkn4bdg.jelita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.smkn4bdg.jelita.riwayat.RiwayatActivity;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText username, password;
    MaterialButton masuk;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findView(){
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        masuk = findViewById(R.id.btn_masuk);
        back = findViewById(R.id.back_login);
    }
}
