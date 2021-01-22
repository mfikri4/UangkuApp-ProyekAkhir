package com.example.uangku;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class adapterpengeluaran extends RecyclerView.Adapter<adapterpengeluaran.myviewholder> {

    private List<pengeluaranmodel> mList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public adapterpengeluaran(List<pengeluaranmodel> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengeluaran,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        pengeluaranmodel model = mList.get(position);
        holder.catatan.setText(model.getCatatan());
        holder.deskripsi.setText(model.getDeskripsi());
        holder.jumlah.setText(model.getJumlah());


        holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogFormPengeluaran dialogForm = new DialogFormPengeluaran(model.getCatatan(),
                        model.getDeskripsi(),
                        model.getJumlah(),
                        model.getKey(),
                        "Ubah");

                dialogForm.show(manager, "form");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{


        TextView catatan, deskripsi, jumlah;
        CardView card_view;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            catatan= itemView.findViewById(R.id.tv_catatan);
            deskripsi= itemView.findViewById(R.id.tv_deskripsi);
            jumlah= itemView.findViewById(R.id.tv_jumlah);

            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
