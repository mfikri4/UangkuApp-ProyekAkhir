package com.example.uangku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Deklarasi Variable
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private ImageButton BtnLogout;
    private ImageButton ibdPemasukan, ibdPengeluaran, btninfo, btnPengeluaran, btnPemasukan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnLogout  = findViewById(R.id.Logout);
        ibdPemasukan = findViewById(R.id.IBDPemasukan);
        ibdPengeluaran = findViewById(R.id.IBDPengeluaran);
        btninfo = findViewById(R.id.IBDInformasi);
        btnPemasukan = findViewById(R.id.ib_Pemasukan);
        btnPengeluaran = findViewById(R.id.ib_Pengeluaran);

        //Instance Firebasee Auth
        auth = FirebaseAuth.getInstance();



        //Menambahkan Listener untuk mengecek apakah user telah logout / keluar
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //Jika Iya atau Null, maka akan berpindah pada halaman Login
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Masuk.class));
                    finish();
                }
            }
        };
        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fungsi untuk logout
                auth.signOut();
            }
        });

        ibdPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datamasuk = new Intent(MainActivity.this,Pemasukan.class);
                startActivity(datamasuk);

            }
        });

        ibdPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datakeluar = new Intent(MainActivity.this,Pengeluaran.class);
                startActivity(datakeluar);
            }
        });


        btninfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent info = new Intent(MainActivity.this,Informasi.class);
                 startActivity(info);
             }
         });

        btnPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormPengeluaran dialogForm = new DialogFormPengeluaran("", "", "", "", "Tambah");
                dialogForm.show(getSupportFragmentManager(), "form");

            }
        });

        btnPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormPemasukan dialogForm = new DialogFormPemasukan("", "", "", "", "Tambah");
                dialogForm.show(getSupportFragmentManager(), "form");

            }
        });
    }

    //Menerapkan Listener
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    //Melepaskan Litener
    @Override
    protected void onStop() {
        super.onStop();
        if(authListener != null){
            auth.removeAuthStateListener(authListener);
        }
    }
}