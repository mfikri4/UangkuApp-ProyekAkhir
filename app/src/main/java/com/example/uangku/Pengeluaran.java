package com.example.uangku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Pengeluaran extends AppCompatActivity {

    FloatingActionButton fab_add;
    RecyclerView recviewpengeluaran;
    adapterpengeluaran adapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<pengeluaranmodel> listPengeluaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        fab_add = findViewById(R.id.fab_add);
        recviewpengeluaran=findViewById(R.id.rv_view_pengeluaran);
        recviewpengeluaran.setLayoutManager(new LinearLayoutManager(this));
        recviewpengeluaran.setItemAnimator(new DefaultItemAnimator());

        fab_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFormPengeluaran dialogForm = new DialogFormPengeluaran("", "", "", "", "Tambah");
                dialogForm.show(getSupportFragmentManager(), "form");

            }
        });

        showData();
    }
    private void showData(){
        database.child("Pengeluaran").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPengeluaran = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){
                    pengeluaranmodel keluar = item.getValue(pengeluaranmodel.class);
                    keluar.setKey(item.getKey());
                    listPengeluaran.add(keluar);
                }

                adapter = new adapterpengeluaran(listPengeluaran, Pengeluaran.this);
                recviewpengeluaran.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void klikKembali (View view){
        Intent i = new Intent(Pengeluaran.this, MainActivity.class);
        startActivity(i);
    }
    
}