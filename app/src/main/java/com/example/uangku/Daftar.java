package com.example.uangku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class Daftar extends AppCompatActivity {

    ImageButton BTNDaftar;
    TextView TVMasuk;
    private EditText myEmail, myPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String getEmail, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        myEmail = findViewById(R.id.et_email);
        myPassword = findViewById(R.id.et_password);
        TVMasuk = findViewById(R.id.tvMasuk);
        BTNDaftar = findViewById(R.id.IB_Daftar);
        auth = FirebaseAuth.getInstance();


        //Menyembunyikan / Hide Password
        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        BTNDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekDataUser();
            }
        });
        TVMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Daftar.this, Masuk.class);
                startActivity(i);
            }
        });


    }

    private void cekDataUser() {
        //Mendapatkan dat yang diinputkan User
        getEmail = myEmail.getText().toString();
        getPassword = myPassword.getText().toString();

        //Mengecek apakah email dan sandi kosong atau tidak
        if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)) {
            Toast.makeText(this, "Email atau Sandi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        } else {
            //Mengecek panjang karakter password baru yang akan didaftarkan
            if (getPassword.length() < 6) {
                Toast.makeText(this, "Sandi Terlalu Pendek, Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                createUserAccount();
            }
        }
    }

    private void createUserAccount() {
        auth.createUserWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Mengecek status keberhasilan saat mendaftarkan email dan sandi baru
                        if (task.isSuccessful()) {
                            Toast.makeText(Daftar.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Daftar.this, "Terjadi Kesalahan, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}