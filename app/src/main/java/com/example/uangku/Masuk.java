package com.example.uangku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Masuk extends AppCompatActivity implements View.OnClickListener{

    private TextView TVDaftar;
    private ImageButton btnMasuk;
    private ProgressBar progressBar;
    private EditText myEmail, myPassword;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;
    private String getEmail, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myEmail = findViewById(R.id.et_email);
        myPassword = findViewById(R.id.et_password);
        btnMasuk = findViewById(R.id.IB_Masuk);
        TVDaftar = findViewById(R.id.tvDaftar);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnMasuk.setOnClickListener(this);
        TVDaftar.setOnClickListener(this);


        //Instance / Membuat Objek Firebase Authentication
        auth = FirebaseAuth.getInstance();

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //Mengecek apakah ada user yang sudah login / belum logout
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Jika ada, maka halaman akan langsung berpidah pada MainActivity
                    startActivity(new Intent(Masuk.this, MainActivity.class));
                    finish();
                }
            }
        };


    }


    //Menerapkan Listener
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    //Melepaskan Litener
    @Override
    protected void onStop() {
        super.onStop();
        if(listener != null){
            auth.removeAuthStateListener(listener);
        }
    }

    //Method ini digunakan untuk proses autentikasi user menggunakan email dan kata sandi
    private void loginUserAccount(){
        auth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Mengecek status keberhasilan saat login
                        if(task.isSuccessful()){
                            Toast.makeText(Masuk.this, "Login Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Masuk.this, "Tidak Dapat Masuk, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tvDaftar:
                Intent a = new Intent(Masuk.this, Daftar.class);
                startActivity(a);

                break;

            case R.id.IB_Masuk:
                //Mendapatkan dat yang diinputkan User
                getEmail = myEmail.getText().toString();
                getPassword = myPassword.getText().toString();

                //Mengecek apakah email dan sandi kosong atau tidak
                if(TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)){
                    Toast.makeText(this, "Email atau Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    loginUserAccount();
                    progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}