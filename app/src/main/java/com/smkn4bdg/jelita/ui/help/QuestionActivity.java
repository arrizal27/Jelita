package com.smkn4bdg.jelita.ui.help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.smkn4bdg.jelita.R;

public class QuestionActivity extends AppCompatActivity {

    MaterialCardView btnkembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_question);

        btnkembali = findViewById(R.id.btn_kembali);
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });
    }
}