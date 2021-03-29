package com.smkn4bdg.jelita.riwayat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelita.Models.DataTransaksi;
import com.smkn4bdg.jelita.R;

import java.util.ArrayList;

public class PendingFragment extends Fragment {
    private ArrayList<DataTransaksi> datatrans;
    private RecyclerView recyclerView;

    public PendingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        recyclerView = view.findViewById(R.id.list_pending);
        recyclerView.setHasFixedSize(true);
        datatrans = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getdata();


        return view;
    }
    private void getdata(){
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("transaksi");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot datasnap : snapshot.getChildren()){
                        DataTransaksi dataTransaksi = datasnap.getValue(DataTransaksi.class);
                        datatrans.add(dataTransaksi);
                    }
                    PendingAdapter pendingAdapter = new PendingAdapter(datatrans);
                    recyclerView.setAdapter(pendingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}