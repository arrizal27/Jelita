package com.smkn4bdg.jelita.ui.setor;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.smkn4bdg.jelita.Models.RequestSetorAdmin;
import com.smkn4bdg.jelita.Models.RequestSetorPengepul;
import com.smkn4bdg.jelita.Models.RequestSetorUser;
import com.smkn4bdg.jelita.Models.SpinnerPengepul;
import com.smkn4bdg.jelita.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class SetorActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_GALLERY = 2;
    private static final int REQUEST_CODE_CAMERA = 1;

    ImageView fotoBukti;
    Spinner listPengepul, listMetodeBayar;
    MaterialButton btnUpload;
    MaterialButton btnBack;
    MaterialButton btnSetorNow;

    DatabaseReference dbPengepul;
    DatabaseReference dbRequestSetorUser;
    DatabaseReference dbRequestSetorUserFinal;
    DatabaseReference dbRequestSetorPengepul;
    DatabaseReference dbRequestSetorPengepulFinal;
    DatabaseReference dbRequestSetorAdmin;
    DatabaseReference dbRequestSetorAdminFinal;
    DatabaseReference dbUser;
    StorageReference storageReference;
    FirebaseUser mUser;
    TextView id_picker, no_picker;
    ArrayList<SpinnerPengepul> pengepul;
    private Uri imageUri = Uri.parse("dummy");
    private String kak = "";
    private String namaPengepul;
    private String noPengepul;
    private String idPengepul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor);

        findView();

        dbPengepul = FirebaseDatabase.getInstance().getReference("pengepul");
        dbRequestSetorUser = FirebaseDatabase.getInstance().getReference("requestSetorUser");
        dbRequestSetorUserFinal = dbRequestSetorUser.push();
        dbRequestSetorPengepul = FirebaseDatabase.getInstance().getReference("requestSetorPengepul");
        dbRequestSetorPengepulFinal = dbRequestSetorPengepul.push();
        dbRequestSetorAdmin = FirebaseDatabase.getInstance().getReference("requestSetorAdmin");
        dbRequestSetorAdminFinal = dbRequestSetorAdmin.push();


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
                getImage();

            }
        });

        btnSetorNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button setor sekarang diklik
                //uploadImage();
                storeDataRequest();

                //storeDataPengepulRequest();


            }
        });

    }

    private void getImage() {
        CharSequence[] menu = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Upload Image")
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent imageIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(imageIntentCamera, REQUEST_CODE_CAMERA);
                                break;

                            case 1:
                                Intent imageIntentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(imageIntentGallery, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK) {

                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    fotoBukti.setImageBitmap(bitmap);
                    imageUri = getImageUri(SetorActivity.this,bitmap);
                    fotoBukti.setImageURI(imageUri);
                    kak = "pap";

                }
                break;
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    fotoBukti.setImageURI(imageUri);
                    kak = "pap";
                }
                break;
        }
    }


    private void storeDataRequest() {
        FirebaseApp.initializeApp(this);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        dbUser = FirebaseDatabase.getInstance().getReference("users");

        fotoBukti.setDrawingCacheEnabled(true);
        fotoBukti.buildDrawingCache();


        System.out.println("PAPAPA : "+imageUri);
        if (kak.equals("")) {
            Toast.makeText(SetorActivity.this,"Tolong upload gambar bukti!", Toast.LENGTH_LONG).show();
        }
            else{
            final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));


            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String image = uri.toString();

                            dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Date c = Calendar.getInstance().getTime();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    String formatdate = sdf.format(c);
                                    String metode_bayar = listMetodeBayar.getSelectedItem().toString();
                                    double total_uang = 0;
                                    String namaUser = null, alamatUser = null, noTelpUser = null;
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        dbUser.child(mUser.getUid()).child("jml_minyak").setValue(0);
                                        String id_storeData = dbRequestSetorUserFinal.getKey();
                                        String id_user = mUser.getUid();
                                        String tanggal_setor = formatdate;
                                        String nama_pengepuls = SetorActivity.this.namaPengepul;
                                        String nomor_pengepul = SetorActivity.this.noPengepul;
                                        String foto_bukti = image;
                                        String jenis_pembayaran = metode_bayar;
                                        String alasan_tolak = "";
                                        String status = "Pending";
                                        if (dataSnapshot.child("id").getValue().equals(mUser.getUid())) {
                                            namaUser = dataSnapshot.child("nama").getValue().toString();
                                            alamatUser = dataSnapshot.child("alamat").getValue().toString();
                                            noTelpUser = dataSnapshot.child("no_tlp").getValue().toString();
//                                            int minyak = Integer.parseInt(dataSnapshot.child("jml_minyak").getValue().toString());
//                                            int poin = Integer.parseInt(dataSnapshot.child("poin").getValue().toString());
//                                            int jumlah_poin = minyak * 10;
//                                            int jumlah_poin_final = poin + jumlah_poin;
//                                            dbUser.child(mUser.getUid()).child("poin").setValue(jumlah_poin_final);

                                            if (metode_bayar.equals("Bayar Langsung")) {
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

                                            if (imageUri.toString().isEmpty()) {
                                                Toast.makeText(getApplicationContext(), "FOTO NYA MANA..... !!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String id_pengepul = SetorActivity.this.idPengepul;
                                                //Setor User
                                                dbRequestSetorUserFinal = FirebaseDatabase.getInstance().getReference("requestSetorUser").child(id_user).child(id_storeData);
                                                RequestSetorUser requestSetorUser1 = new RequestSetorUser(id_storeData,id_pengepul, nama_pengepuls, nomor_pengepul, alamatUser,
                                                        tanggal_setor, foto_bukti, jenis_pembayaran, alasan_tolak, total_uang, status);
                                                dbRequestSetorUserFinal.setValue(requestSetorUser1);

                                                //Setor Pengepul
                                                dbRequestSetorPengepulFinal = FirebaseDatabase.getInstance().getReference("requestSetorPengepul").child(id_pengepul).child(id_storeData);
                                                RequestSetorPengepul requestSetorPengepul = new RequestSetorPengepul(id_storeData,id_user, namaUser, alamatUser, noTelpUser, tanggal_setor, foto_bukti, jenis_pembayaran, alasan_tolak, total_uang, status);
                                                dbRequestSetorPengepulFinal.setValue(requestSetorPengepul);

                                                //Setor Admin
                                                dbRequestSetorAdminFinal = FirebaseDatabase.getInstance().getReference("requestSetorAdmin").child(id_storeData);
                                                RequestSetorAdmin requestSetorAdmin = new RequestSetorAdmin(id_storeData,namaUser, nama_pengepuls,status,tanggal_setor,alamatUser,jenis_pembayaran,total_uang,nomor_pengepul,foto_bukti);
                                                dbRequestSetorAdminFinal.setValue(requestSetorAdmin);
                                                
                                                Intent i = new Intent(SetorActivity.this, SetorBerhasilActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Upload Gagal !!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(), "FOTO NYA MANA....!!!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }

            });
        }



    }


    private void findView() {
        listPengepul = findViewById(R.id.list_pengepul);
        listMetodeBayar = findViewById(R.id.list_metode_bayar);
        fotoBukti = findViewById(R.id.view_foto_bukti);
        btnUpload = findViewById(R.id.upload_foto);
        btnBack = findViewById(R.id.btn_back);
        btnSetorNow = findViewById(R.id.btn_setor_now);

        no_picker = findViewById(R.id.no_telp_pengepul);
    }


    private void fetchData() {
        pengepul = new ArrayList<SpinnerPengepul>();
        dbUser = FirebaseDatabase.getInstance().getReference("users");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<String> listPengepulsNama = new ArrayList<String>();
        dbPengepul.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot snapshot) {
                final List<String> pengepulItems = new ArrayList<String>();
                for (final DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    dbUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                SpinnerPengepul Sp = dataSnapshot.getValue(SpinnerPengepul.class);
                                if (dataSnapshot1.child("id").getValue().equals(mUser.getUid())) {
                                    if (dataSnapshot1.child("kecamatan").getValue().toString().equals(dataSnapshot.child("kecamatan").getValue().toString())) {
                                        listPengepulsNama.add(Sp.getNama());
                                        pengepul.add(Sp);
                                    }
                                }
                            }
                            ArrayAdapter<String> pengepulAdapter = new ArrayAdapter<String>(SetorActivity.this,
                                    android.R.layout.simple_spinner_item, listPengepulsNama);
                            pengepulAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            listPengepul.setAdapter(pengepulAdapter);

                            listPengepul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    idPengepul = pengepul.get(i).getId();
                                    noPengepul = pengepul.get(i).getNo_telp();
                                    namaPengepul = pengepul.get(i).getNama();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
//                SpinnerPengepulAdapter spinnerPengepulAdapter = new SpinnerPengepulAdapter(SetorActivity.this,
//                        listPengepulsNama, listPengepulsNoTelp,listPengepulsAlamat);


                //SpinnerPengepulAdapter c = new SpinnerPengepulAdapter(SetorActivity.this,.getNama_pengepul(),pengepul.get(1).getNo_telp(),pengepul.get(2).getAlamat());
//                ArrayAdapter<String> adapterPengepul = new ArrayAdapter<String>(SetorActivity)
//                listPengepul.setAdapter(c);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String[] metode_bayar = {"Bayar Langsung", "Credit"};

        ArrayAdapter<String> metodeAdapter = new ArrayAdapter<String>(SetorActivity.this, android.R.layout.simple_spinner_item, metode_bayar);
        metodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listMetodeBayar.setAdapter(metodeAdapter);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}