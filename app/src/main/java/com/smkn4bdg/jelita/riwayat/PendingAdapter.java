package com.smkn4bdg.jelita.riwayat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smkn4bdg.jelita.Models.DataTransaksi;
import com.smkn4bdg.jelita.R;

import java.util.ArrayList;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder> {
    private ArrayList<DataTransaksi> dataTrans;

    public PendingAdapter(ArrayList<DataTransaksi> dataTrans){
        this.dataTrans = dataTrans;
    }

    @NonNull
    @Override
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_setoran, parent,false);
        return new PendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingViewHolder holder, int position) {
        DataTransaksi dataTransaksi = dataTrans.get(position);
        holder.tvpengepul.setText(dataTransaksi.getPengepul());
        holder.tvtelepon.setText(dataTransaksi.getTelepon());
        holder.tvalamat.setText(dataTransaksi.getAlamat());
        holder.tvtanggalsetor.setText(dataTransaksi.getTanggalsetor());
        holder.tvjenispembayaran.setText(dataTransaksi.getStatus());
        holder.tvalasan.setText(dataTransaksi.getAlasan());

    }

    @Override
    public int getItemCount() {
        return dataTrans.size();
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder {
        TextView tvpengepul,tvtelepon,tvalamat,tvtanggalsetor,tvjenispembayaran,tvstatus,tvalasan;
        ImageView ivbukti;
        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpengepul = itemView.findViewById(R.id.txt_notelp);
            tvtelepon = itemView.findViewById(R.id.txt_notelp);
            tvalamat = itemView.findViewById(R.id.txt_alamat);
            tvtanggalsetor = itemView.findViewById(R.id.txt_tgl_setor);
            tvjenispembayaran = itemView.findViewById(R.id.txt_jenis_bayar);
            tvstatus = itemView.findViewById(R.id.txt_status);
            tvalasan = itemView.findViewById(R.id.txt_alasan);


        }
    }
}
