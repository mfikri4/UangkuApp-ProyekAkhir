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

public class Pemasukan extends AppCompatActivity {

    FloatingActionButton fab_add;
    RecyclerView recviewpemasukan;
    myadapterpemasukan adapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<modelpemasukan> listPemasukan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        fab_add = findViewById(R.id.fab_add);
        recviewpemasukan=findViewById(R.id.rv_view_pemasukan);
        recviewpemasukan.setLayoutManager(new LinearLayoutManager(this));
        recviewpemasukan.setItemAnimator(new DefaultItemAnimator());

        fab_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFormPemasukan dialogForm = new DialogFormPemasukan("", "", "", "", "Tambah");
                dialogForm.show(getSupportFragmentManager(), "form");

            }
        });
        showData();
    }
    private void showData(){
        database.child("Pemasukan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPemasukan = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){
                    modelpemasukan masuk = item.getValue(modelpemasukan.class);
                    masuk.setKey(item.getKey());
                    listPemasukan.add(masuk);
                }

                adapter = new myadapterpemasukan(listPemasukan, Pemasukan.this);
                recviewpemasukan.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void klikKembali (View view){
        Intent i = new Intent(Pemasukan.this, MainActivity.class);
        startActivity(i);
    }

}