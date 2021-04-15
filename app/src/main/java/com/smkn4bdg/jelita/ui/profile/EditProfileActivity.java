package com.smkn4bdg.jelita.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.R;

public class EditProfileActivity extends AppCompatActivity {
    MaterialButton simpan;
    MaterialButton back;
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mUser;
    TextInputEditText nama, username, email, notelp, alamat, kota, kelurahan, kecamatan;
    String id, jeniskel, role;
    int poin, jmlminyak;
    private final String TAG = this.getClass().getName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mdbUsers = FirebaseDatabase.getInstance().getReference();
        mUser = firebaseAuth.getCurrentUser();

        userInformation(mUser.getUid());
        findView();
        email.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setNama(nama.getText().toString());
                user.setUsername(username.getText().toString());
                user.setNo_tlp(notelp.getText().toString());
                user.setAlamat(alamat.getText().toString());
                user.setKota(kota.getText().toString());
                user.setEmail(email.getText().toString());
                user.setKecamatan(kecamatan.getText().toString());
                user.setKelurahan(kelurahan.getText().toString());
                user.setId(id);
                user.setRole(role);
                user.setJenis_kelamin(jeniskel);
                user.setPoin(poin);
                user.setJml_minyak(jmlminyak);
                mdbUsers.child("users").child(mUser.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(EditProfileActivity.this, EditSuccessActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        });
    }
    private void userInformation(String uID) {
        final Query q = mdbUsers.child("users").child(uID);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User Uinfo = dataSnapshot.getValue(User.class);
                    setData(Uinfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setData(User info) {
        id = info.getId();
        notelp.setText(info.getNo_tlp());
        nama.setText(info.getNama());
        username.setText(info.getUsername());
        email.setText(info.getEmail());
        alamat.setText(info.getAlamat());
        kota.setText(info.getKota());
        kecamatan.setText(info.getKecamatan());
        kelurahan.setText(info.getKelurahan());
        jeniskel = info.getJenis_kelamin();
        role = info.getRole();
        poin = info.getPoin();
        jmlminyak = info.getJml_minyak();
    }

    private void findView(){
        simpan = findViewById(R.id.btnsimpan);
        back = findViewById(R.id.btnkembali);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        notelp = findViewById(R.id.notelp);
        alamat = findViewById(R.id.alamat);
        kota = findViewById(R.id.kota);
        kecamatan = findViewById(R.id.kecamatan);
        kelurahan = findViewById(R.id.kelurahan);

    }
}