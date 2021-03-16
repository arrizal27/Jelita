package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SetorActivity extends AppCompatActivity{

    ImageView fotoBukti;
    Spinner listPengepul, listMetodeBayar;

    MaterialButton btnUpload;
    MaterialButton btnBack;
    MaterialButton btnSetorNow;

    //data dummy dropdown, hapus aja nanti
    String[] bayar = {"Uang Tunai", "Sembako - Beras", "Poin"};
    String[] p = {"Pengepul A", "Pengepul B", "Pengepul C"};
    String[] telp = {"0812345678", "0812345678911", "0812345678910"};
    String[] alamat = {"Turangga", "Cibaduyut", "Banjaran"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor);

        findView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button upload diklik

            }
        });

        btnSetorNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button setor sekarang diklik
            }
        });

        //dropdown metode bayar
        ArrayAdapter b = new ArrayAdapter(this,android.R.layout.simple_spinner_item, bayar);
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listMetodeBayar.setAdapter(b);

        //dropdown pengepul
        SpinnerPengepulAdapter c = new SpinnerPengepulAdapter(getApplicationContext(), p, telp, alamat);
        listPengepul.setAdapter(c);
    }

    private void findView(){
        listPengepul = findViewById(R.id.list_pengepul);
        listMetodeBayar = findViewById(R.id.list_metode_bayar);
        fotoBukti = findViewById(R.id.view_foto_bukti);
        btnUpload = findViewById(R.id.upload_foto);
        btnBack = findViewById(R.id.btn_back);
        btnSetorNow = findViewById(R.id.btn_setor_now);
    }
}