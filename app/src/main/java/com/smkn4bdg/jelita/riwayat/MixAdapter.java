package com.smkn4bdg.jelita.riwayat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smkn4bdg.jelita.Models.RequestSetorUser;
import com.smkn4bdg.jelita.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MixAdapter extends RecyclerView.Adapter<MixAdapter.MixViewHolder> {
    private ArrayList<RequestSetorUser> dataSetor;

    public MixAdapter(ArrayList<RequestSetorUser> dataSetor) {
        this.dataSetor = dataSetor;
    }

    @NonNull
    @Override
    public MixViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_setoran, parent,false);
        return new MixViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MixViewHolder holder, int position) {
        RequestSetorUser requestSetorUser = dataSetor.get(position);
        NumberFormat nm = NumberFormat.getInstance();
        Picasso.get().load(requestSetorUser.getFoto_bukti()).into(holder.ivbukti);
        holder.tvpengepul.setText(requestSetorUser.getNama_pengepul());
        holder.tvtelepon.setText(requestSetorUser.getNo_telp_pengepul());
        holder.tvalamat.setText(requestSetorUser.getAlamat_user());
        holder.tvstatus.setText(requestSetorUser.getStatus());
        holder.tvtanggalsetor.setText(requestSetorUser.getTanggal_setor());
        holder.tvjenispembayaran.setText(requestSetorUser.getJenis_pembayaran());
        holder.tvbayar.setText("Rp."+nm.format(requestSetorUser.getTotal_uang()));
        holder.tvalasan.setText(requestSetorUser.getAlasantolak());
        holder.tvalasan.setVisibility(View.GONE);
        holder.txtalasan.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return dataSetor.size();
    }

    public class MixViewHolder extends RecyclerView.ViewHolder {
        TextView tvpengepul,tvtelepon,tvalamat,tvtanggalsetor,tvjenispembayaran,tvstatus,tvalasan,txtalasan,tvbayar;
        ImageView ivbukti;
        public MixViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpengepul = itemView.findViewById(R.id.txt_pengepul);
            tvtelepon = itemView.findViewById(R.id.txt_notelp);
            tvalamat = itemView.findViewById(R.id.txt_alamat);
            tvtanggalsetor = itemView.findViewById(R.id.txt_tgl_setor);
            tvjenispembayaran = itemView.findViewById(R.id.txt_jenis_bayar);
            tvstatus = itemView.findViewById(R.id.txt_status);
            tvalasan = itemView.findViewById(R.id.txt_alasan);
            txtalasan = itemView.findViewById(R.id.ttl_alasan);
            tvbayar = itemView.findViewById(R.id.txt_bayar);
            ivbukti = itemView.findViewById(R.id.foto_bukti);


        }
    }
}

