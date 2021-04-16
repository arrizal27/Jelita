package com.smkn4bdg.jelita.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    MaterialButton simpan;
    MaterialButton back;
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri imageUri;
    TextInputEditText nama, username, email, notelp, alamat, kota, kelurahan, kecamatan;
    Spinner jeniskel;
    String id, role, foto;
    int poin, jmlminyak;
    Button file;
    ImageView imgView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private static final int REQUEST_CODE_GALLERY = 2;
    private static final int REQUEST_CODE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mdbUsers = FirebaseDatabase.getInstance().getReference();
        mUser = firebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        userInformation(mUser.getUid());
        findView();
        email.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri != null) {
                    imgView.setDrawingCacheEnabled(true);
                    imgView.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();

                    String namaFile = UUID.randomUUID() + ".jpg";
                    String pathImage = "File/" + namaFile;

                    UploadTask uploadTask = storageReference.child(pathImage).putBytes(bytes);

                    final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String image = uri.toString();
                            User user = new User();
                            user.setNama(nama.getText().toString());
                            user.setUsername(username.getText().toString());
                            user.setNo_tlp(notelp.getText().toString());
                            user.setAlamat(alamat.getText().toString());
                            user.setKota(kota.getText().toString());
                            user.setEmail(email.getText().toString());
                            user.setKecamatan(kecamatan.getText().toString());
                            user.setKelurahan(kelurahan.getText().toString());
                            user.setFoto(image);
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
                } else {
                    User user = new User();
                    user.setNama(nama.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setNo_tlp(notelp.getText().toString());
                    user.setAlamat(alamat.getText().toString());
                    user.setKota(kota.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setKecamatan(kecamatan.getText().toString());
                    user.setKelurahan(kelurahan.getText().toString());
                    user.setFoto(foto);
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
        jeniskel.setSelection( ((ArrayAdapter) jeniskel.getAdapter()).getPosition(info.getJenis_kelamin()));
        foto = info.getFoto();
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
        imgView = findViewById(R.id.imgView);
        file = findViewById(R.id.chooseFile);
        jeniskel = findViewById(R.id.dropdown_gender);
    }

    private void chooseImage(){
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
                    imgView.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    imgView.setImageURI(imageUri);
                }
                break;
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}