package com.smkn4bdg.jelita.ui.setor;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smkn4bdg.jelita.Models.RequestSetorUser;
import com.smkn4bdg.jelita.Models.SpinnerPengepul;
import com.smkn4bdg.jelita.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SetorActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView fotoBukti;
    Spinner listPengepul, listMetodeBayar;
    String currentPhotoPath;
    MaterialButton btnUpload;
    MaterialButton btnBack;
    MaterialButton btnSetorNow;
    EditText test;

    DatabaseReference dbPengepul;
    DatabaseReference dbRequestSetor;
    DatabaseReference dbRequestSetorFinal;
    DatabaseReference dbUser;
    StorageReference storageReference;
    private Uri imageUri;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor);

        findView();

        dbPengepul = FirebaseDatabase.getInstance().getReference("pengepul");
        dbRequestSetor = FirebaseDatabase.getInstance().getReference("requestSetorUser");
        dbRequestSetorFinal = dbRequestSetor.push();

        storageReference = FirebaseStorage.getInstance().getReference();
        fetchData();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });

        btnSetorNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button setor sekarang diklik
                //uploadImage();
                storeDataRequest();


                Intent i = new Intent(SetorActivity.this, SetorBerhasilActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            fotoBukti.setImageURI(imageUri);
        }
    }


    private void storeDataRequest() {
        SpinnerPengepul spinnerPengepulNama = new SpinnerPengepul();
        SpinnerPengepul spinnerPengepulNoTelp = new SpinnerPengepul();

        FirebaseApp.initializeApp(this);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        dbUser = FirebaseDatabase.getInstance().getReference("users");

        String nama_pengepul;
        String no_telp_pengepul;

        final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));


        listPengepul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String image = uri.toString();

                        dbUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String formatdate = sdf.format(c);
                                String nama_pul = spinnerPengepulNama.setNama_pengepul(listPengepul.getSelectedItem().toString());
                                String nomor_pul = spinnerPengepulNoTelp.setNo_telp(listPengepul.getSelectedItem().toString());
                                String metode_bayar = listMetodeBayar.getSelectedItem().toString();
                                double total_uang = 0;

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    dbUser.child(mUser.getUid()).child("jml_minyak").setValue(0);
                                    String id_storeData = dbRequestSetorFinal.getKey();
                                    String id_user = mUser.getUid();
                                    String alamat_user = dataSnapshot.child("alamat").getValue().toString();
                                    String tanggal_setor = formatdate;
                                    String nama_pengepul = nama_pul;
                                    String nomor_pengepul = nomor_pul;
                                    String foto_bukti = image;
                                    String jenis_pembayaran = metode_bayar;
                                    String alasan_tolak = "Gk papa";
                                    if (dataSnapshot.child("id").getValue().equals(mUser.getUid())) {
                                        if (dataSnapshot.child("role").getValue().toString().equals("Rumah Tangga")) {
                                            total_uang = 15000;
                                        }
                                        if (dataSnapshot.child("role").getValue().toString().equals("Pedagang")) {
                                            total_uang = 30000;
                                        }
                                        if (dataSnapshot.child("role").getValue().toString().equals("Cafe dan Rumah Makan")) {
                                            total_uang = 45000;
                                        }
                                        if (dataSnapshot.child("role").getValue().toString().equals("Hotel dan Penginapan")) {
                                            total_uang = 60000;
                                        }
                                    }


                                    dbRequestSetorFinal = FirebaseDatabase.getInstance().getReference("requestSetorUser").child(id_user).child(id_storeData);
                                    RequestSetorUser requestSetorUser1 = new RequestSetorUser(id_storeData, nama_pengepul, nomor_pengepul, alamat_user,
                                            tanggal_setor, foto_bukti, jenis_pembayaran, alasan_tolak, total_uang, false, false, true, false);
                                    System.out.println(requestSetorUser1);
                                    dbRequestSetorFinal.setValue(requestSetorUser1);

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
            }
        });


        System.out.println("KELUARRRRR");

    }


    private void findView() {
        listPengepul = findViewById(R.id.list_pengepul);
        listMetodeBayar = findViewById(R.id.list_metode_bayar);
        fotoBukti = findViewById(R.id.view_foto_bukti);
        btnUpload = findViewById(R.id.upload_foto);
        btnBack = findViewById(R.id.btn_back);
        btnSetorNow = findViewById(R.id.btn_setor_now);
    }

    private void fetchData() {
        dbPengepul.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final List<SpinnerPengepul> pengepul = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id_pengepul = dataSnapshot.child("id").getValue().toString();
                    String nama_pengepul = dataSnapshot.child("nama").getValue().toString();
                    String no_telp = dataSnapshot.child("no_telp").getValue().toString();
                    String alamat = dataSnapshot.child("alamat").getValue().toString();

                    pengepul.add(new SpinnerPengepul(id_pengepul, nama_pengepul, no_telp, alamat));
//                    pengepul.add(no_telp);
//                    pengepul.add(alamat);

                }
                ArrayAdapter<SpinnerPengepul> pengepulAdapter = new ArrayAdapter<SpinnerPengepul>(SetorActivity.this,
                        android.R.layout.simple_spinner_item, pengepul);
                pengepulAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listPengepul.setAdapter(pengepulAdapter);
                //SpinnerPengepulAdapter c = new SpinnerPengepulAdapter(SetorActivity.this,.getNama_pengepul(),pengepul.get(1).getNo_telp(),pengepul.get(2).getAlamat());
//                ArrayAdapter<String> adapterPengepul = new ArrayAdapter<String>(SetorActivity)
//                listPengepul.setAdapter(c);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String[] metode_bayar = {"Bayar Langsung", "Credit"};

        ArrayAdapter<String> metodeAdapter = new ArrayAdapter<String>(SetorActivity.this, android.R.layout.simple_spinner_item,metode_bayar);
        metodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listMetodeBayar.setAdapter(metodeAdapter);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}