package com.smkn4bdg.jelita.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.ui.main.MainActivity;
import com.smkn4bdg.jelita.ui.WelcomePageActivity;


public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 234;

    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbUsers;
    private static final String TAG = "LoginActivity";
    User user = new User();
    EditText username;
    TextInputEditText pass;
    Button btnLogin;
    ImageButton googlelogin, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.login();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(LoginActivity.this, WelcomePageActivity.class);
                startActivity(back);
                finish();
            }
        });

        //Login Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.googleSign();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //auto login process
        //move to main activity if user already sign in
        if (firebaseAuth.getCurrentUser() != null) {
            // User is logged in
            System.out.println("Email : " + firebaseAuth.getCurrentUser().getEmail());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //data default
        String jenisKelamin = "tidak disebutkan";
        String noTlp = "08**********";
        int jml_minyak = 0;
        int poin = 0;
        String alamat = "";
        String kelurahan = "";
        String kecamatan = "";
        String kota = "";
        String role = "Rumah Tangga";

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "signInWithCredential:success");
                        } else {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String id = firebaseAuth.getUid();
                            String name = user.getDisplayName();
                            String Gusername = user.getDisplayName();
                            String Gmail = user.getEmail();

                            User usr = new User(id, name, Gusername, role, Gmail,
                                    jenisKelamin, noTlp, jml_minyak, poin, alamat, kelurahan
                                    , kecamatan, kota);

                            dbUsers.child("Pengguna").child(id).setValue(usr);
                            Toast.makeText(LoginActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();

                        }

                        // ...
                    }
                });
    }

    private void findView(){
        username = findViewById(R.id.login_username);
        pass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_masuk);
        back = findViewById(R.id.back_login);
        googlelogin = findViewById(R.id.googlesignin);
        dbUsers = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void login(){
        String usernameFinal = username.getText().toString();
        final String passFinal = pass.getText().toString();

        if (TextUtils.isEmpty(usernameFinal)) {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passFinal)) {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //login user
        firebaseAuth.signInWithEmailAndPassword(usernameFinal, passFinal)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {

                            if (passFinal.length() < 6) {
                                pass.setError(LoginActivity.this.getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                            user.setEmail(usernameFinal);
                        }
                    }
                });

    }

    private void googleSign(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}