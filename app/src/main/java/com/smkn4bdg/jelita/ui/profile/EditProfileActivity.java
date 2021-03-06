package com.smkn4bdg.jelita.ui.profile;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    MaterialButton simpan;
    MaterialButton back;
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mUser;
    TextInputEditText nama, username, email, notelp, alamat, kota, kelurahan, kecamatan;
    String id, role,oldUri = "kak";
    Spinner jeniskel;
    Button btn_gbr;
    private Uri imageUri = Uri.parse("dummy");
    private StorageReference reference;
    ImageView gbr;
    int poin, jmlminyak;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
    private final String TAG = this.getClass().getName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mdbUsers = FirebaseDatabase.getInstance().getReference();
        mUser = firebaseAuth.getCurrentUser();
        reference = FirebaseStorage.getInstance().getReference();

        userInformation(mUser.getUid());
        findView();
        email.setEnabled(false);
        btn_gbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Menerapkan kejadian saat tombol pilih gambar di klik
                getImage();
            }
        });
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
                gbr.setDrawingCacheEnabled(true);
                gbr.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) gbr.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //Mengkompress bitmap menjadi JPG dengan kualitas gambar 100%
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                //Lokasi lengkap dimana gambar akan disimpan
                String namaFile = UUID.randomUUID() + ".jpg";
                String pathImage = "File/" + namaFile;
                System.out.println("FILEE : " + imageUri);
                if (imageUri.toString().equals("dummy")) {
                    Toast.makeText(EditProfileActivity.this,"Tolong upload ulang gambar!", Toast.LENGTH_LONG).show();
                } else {
                UploadTask uploadTask = reference.child(pathImage).putBytes(bytes);
                final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        progressBar.setVisibility(View.VISIBLE);
                    }
                });

                fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String image = uri.toString();
                                user.setNama(nama.getText().toString());
                                user.setUsername(username.getText().toString());
                                user.setNo_tlp(notelp.getText().toString());
                                user.setAlamat(alamat.getText().toString());
                                user.setKota(kota.getText().toString());
                                user.setEmail(email.getText().toString());
                                user.setKecamatan(kecamatan.getText().toString());
                                user.setKelurahan(kelurahan.getText().toString());
                                if (image.isEmpty()) {
                                    user.setFoto(oldUri);
                                } else {
                                    user.setFoto(image);
                                }
                                user.setId(id);
                                user.setRole(role);
                                user.setJenis_kelamin(jeniskel.getSelectedItem().toString());
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
                });
            }
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
        oldUri = info.getFoto();
        role = info.getRole();
        if (info.getFoto().isEmpty()){

        }
        else{
            Picasso.get().load(info.getFoto()).into(gbr);
        }
        poin = info.getPoin();
        jmlminyak = info.getJml_minyak();
        jeniskel.setSelection( ((ArrayAdapter) jeniskel.getAdapter()).getPosition(info.getJenis_kelamin()));
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
        btn_gbr = findViewById(R.id.btn_gbr);
        gbr = findViewById(R.id.gbr);
        kecamatan = findViewById(R.id.kecamatan);
        kelurahan = findViewById(R.id.kelurahan);
        jeniskel = findViewById(R.id.dropdown_gender);

    }
    private void getImage(){
        CharSequence[] menu = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Upload Image")
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //Mengambil gambar dari Kemara ponsel
                                Intent imageIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(imageIntentCamera, REQUEST_CODE_CAMERA);
                                break;

                            case 1:
                                //Mengambil gambar dari galeri
                                Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        switch(requestCode){
            case REQUEST_CODE_CAMERA:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    gbr.setImageBitmap(bitmap);
                    imageUri = getImageUri(EditProfileActivity.this,bitmap);
                    gbr.setImageURI(imageUri);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    imageUri = data.getData();
                    gbr.setImageURI(imageUri);
                }
                break;
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}