package com.example.uangku;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogFormPengeluaran extends DialogFragment {

    String  catatan, deskripsi, jumlah,key,pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogFormPengeluaran(String catatan, String deskripsi, String jumlah, String key, String pilih) {
        this.catatan = catatan;
        this.deskripsi = deskripsi;
        this.jumlah = jumlah;
        this.key = key;
        this.pilih = pilih;
    }

    TextView etCatatan,etJumlah, etDeskripsi;

    ImageButton btnSimpan,btnHapus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.input_form,container, false);

        etCatatan = view.findViewById(R.id.et_catatan);
        etDeskripsi = view.findViewById(R.id.et_deskripsi);
        etJumlah = view.findViewById(R.id.et_jumlah);
        btnSimpan = view.findViewById(R.id.btn_simpan);
        btnHapus = view.findViewById(R.id.btn_hapus);

        etCatatan.setText(catatan);
        etDeskripsi.setText(deskripsi);
        etJumlah.setText(jumlah);


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catatan = etCatatan.getText().toString();
                String deskripsi = etDeskripsi.getText().toString();
                String jumlah = etJumlah.getText().toString();

                if (TextUtils.isEmpty(catatan)) {
                    input((EditText) etCatatan, "catatan");
                } else if (TextUtils.isEmpty(deskripsi)) {
                    input((EditText) etDeskripsi, "deskripsi");
                } else if (TextUtils.isEmpty(jumlah)) {
                    input((EditText) etJumlah, "jumlah");
                } else {
                    if(pilih.equals("Tambah")){
                        database.child("Pengeluaran").push().setValue(new pengeluaranmodel(catatan, deskripsi, jumlah)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                etCatatan.setText("");
                                etDeskripsi.setText("");
                                etJumlah.setText("");
                                Toast.makeText(view.getContext(), "Data berhasi disimpan", Toast.LENGTH_SHORT).show();
                                Intent awal = new Intent(getContext(),Pengeluaran.class);
                                startActivity(awal);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data gagal tersimpan", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else if(pilih.equals("Ubah")){
                        database.child("Pengeluaran").child(key).setValue(new pengeluaranmodel(catatan, deskripsi, jumlah)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                etCatatan.setText("");
                                etDeskripsi.setText("");
                                etJumlah.setText("");
                                Toast.makeText(view.getContext(), "Data berhasi diubah", Toast.LENGTH_SHORT).show();
                                Intent awal = new Intent(getContext(),Pengeluaran.class);
                                startActivity(awal);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data gagal diubah", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                database.child("Pengeluaran").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(view.getContext(), "Data berhasi dihapus", Toast.LENGTH_SHORT).show();
                        Intent awal = new Intent(getContext(),Pengeluaran.class);
                        startActivity(awal);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void input(EditText txt, String s){
        txt.setError(s+" tidak boleh kosong");
        txt.requestFocus();
    }
}