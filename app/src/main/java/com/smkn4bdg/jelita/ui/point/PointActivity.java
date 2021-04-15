package com.smkn4bdg.jelita.ui.point;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.smkn4bdg.jelita.R;

public class PointActivity extends AppCompatActivity {
    TextView poin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        findView();
//        String po = getIntent().getStringExtra("data");
//        poin.setText(po);

    }
    private void findView(){
        poin = findViewById(R.id.tv_jumlahPoint);
    }
}