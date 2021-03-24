package com.smkn4bdg.jelita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PilihRoleActivity extends AppCompatActivity {
    private Button mPengepul, mPengguna;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_role);

        mPengepul = (Button) findViewById(R.id.pengepul);
        mPengguna = (Button) findViewById(R.id.pengguna);

        mPengepul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PilihRoleActivity.this, DaftarActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
