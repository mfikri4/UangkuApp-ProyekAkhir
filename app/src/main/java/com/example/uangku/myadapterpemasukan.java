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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class myadapterpemasukan extends RecyclerView.Adapter<myadapterpemasukan.myviewholder> {

    private List<modelpemasukan> mList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    public myadapterpemasukan(List<modelpemasukan> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pemasukan,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        modelpemasukan model = mList.get(position);
        holder.catatan.setText(model.getCatatan());
        holder.deskripsi.setText(model.getDeskripsi());
        holder.jumlah.setText(model.getJumlah());

        holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogFormPemasukan dialogForm = new DialogFormPemasukan(model.getCatatan(),
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
