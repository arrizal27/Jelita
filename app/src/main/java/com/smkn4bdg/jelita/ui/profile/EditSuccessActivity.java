package com.smkn4bdg.jelita.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.smkn4bdg.jelita.R;

public class EditSuccessActivity extends AppCompatActivity {
    MaterialButton btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_berhasil);

        btn = findViewById(R.id.btn_berhasil);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditSuccessActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
