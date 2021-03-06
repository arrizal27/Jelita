package com.smkn4bdg.jelita.ui.setor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelita.Models.SpinnerPengepul;
import com.smkn4bdg.jelita.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerPengepulAdapter extends BaseAdapter {
    private ArrayList pengepul;
    private ArrayList noTelp;
    private ArrayList alamat;
    private LayoutInflater inflater;
    private int countPengepul;

    DatabaseReference mdbPengepul = FirebaseDatabase.getInstance().getReference();




    SpinnerPengepulAdapter(Context context, ArrayList pengepul, ArrayList noTelp, ArrayList alamat) {
        this.pengepul = pengepul;
        this.noTelp = noTelp;
        this.alamat = alamat;
        inflater = (LayoutInflater.from(context));
    }

    public void getcount(){
        mdbPengepul.child("pengepul").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countPengepul = (int)snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getCount() {

            getcount();



            return SpinnerPengepulAdapter.this.countPengepul;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.card_dropdown_pengepul, null);
        TextView namaPengepul = view.findViewById(R.id.nama_pengepul);
        TextView notelpPengepul = view.findViewById(R.id.no_telp_pengepul);
        TextView alamatPengepul = view.findViewById(R.id.alamat_pengepul);



        namaPengepul.setText(pengepul.toString());
        notelpPengepul.setText(noTelp.toString());
        alamatPengepul.setText(alamat.toString());

        return view;
    }
}
