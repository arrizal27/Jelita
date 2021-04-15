package com.smkn4bdg.jelita.ui.help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.ui.main.MainActivity;

public class TutorialActivity extends AppCompatActivity {

    MaterialCardView btnkembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_tutorial);

        btnkembali = findViewById(R.id.btn_kembali);
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TutorialActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });
    }
}