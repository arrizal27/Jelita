package com.smkn4bdg.jelita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelita.LoginActivity;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.WelcomePageActivity;

public class DaftarActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbUsers;
    private static final String TAG = "DaftarActivity";
    EditText nama,username,pass,emailhp;
    Spinner role;
    Button btndaftar;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        findView();

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(DaftarActivity.this, WelcomePageActivity.class);
                startActivity(back);
                finish();
            }
        });

    }

    private void findView(){
        nama = findViewById(R.id.txt_nama);
        username = findViewById(R.id.txt_username);
        pass = findViewById(R.id.txt_password);
        emailhp = findViewById(R.id.txt_telp);
        role = findViewById(R.id.dropdown_role);
        btndaftar = findViewById(R.id.btn_daftar);
        back = findViewById(R.id.back_daftar);

        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void daftar(){
        //get data from Form daftar
        String namaFinal = nama.getText().toString();
        String usernameFinal = username.getText().toString();
        String passFinal = pass.getText().toString();
        String emailhpFinal = emailhp.getText().toString();
        String roleFinal = role.getSelectedItem().toString();

        //data default
        String jenisKelamin = "tidak disebutkan";
        String noTlp = "08**********";
        int jml_minyak = 0;
        int poin = 0;
        String alamat = "";
        String kelurahan = "";
        String kecamatan = "";
        String kota = "";

        if (TextUtils.isEmpty(namaFinal)) {
            showToast("Enter Your Name!");
            return;
        }
        if (TextUtils.isEmpty(usernameFinal)) {
            showToast("Enter Username!");
            return;
        }
        if (TextUtils.isEmpty(passFinal)) {
            showToast("Enter Your Password!");
            return;
        }
        if (TextUtils.isEmpty(emailhpFinal)) {
            showToast("Enter email address!");
            return;
        }
        if (roleFinal == null) {
            showToast("Enter Your Roles!");
            return;
        }

        dbUsers.orderByChild("email").equalTo(emailhpFinal).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    showToast("Email Already Exist!");
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(emailhpFinal, passFinal)
                            .addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "New user registration: " + task.isSuccessful());
                                    if (!task.isSuccessful()) {
                                        DaftarActivity.this.showToast("Authentication failed. " + task.getException());
                                    } else {
                                        String id = firebaseAuth.getUid();
                                        User user = new User(id, namaFinal, usernameFinal, roleFinal, emailhpFinal,
                                                passFinal, jenisKelamin, noTlp, jml_minyak, poin, alamat, kelurahan
                                                , kecamatan, kota);

                                        dbUsers.child(id).setValue(user);
                                        DaftarActivity.this.startActivity(new Intent(DaftarActivity.this, DaftarBerhasilActivity.class));
                                        DaftarActivity.this.finish();

                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}