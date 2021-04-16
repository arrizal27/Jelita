package com.smkn4bdg.jelita.ui.point;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smkn4bdg.jelita.Models.DataReward;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.riwayat.DitolakAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.PointViewHolder> {
    private ArrayList<DataReward> list;

    public PointAdapter(ArrayList<DataReward> datalist){
        this.list = datalist;
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_poin, parent,false);
        return new PointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        DataReward dataReward  = list.get(position);
        Picasso.get().load(dataReward.getFoto()).into(holder.ivimage);
        holder.tvtitle.setText(dataReward.getNama());
        holder.tvharga.setText(dataReward.getHarga());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PointViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle, tvharga;
        ImageView ivimage;
        Button btnBuy;
        public PointViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimage = itemView.findViewById(R.id.img_point);
            tvtitle = itemView.findViewById(R.id.tv_title);
            tvharga = itemView.findViewById(R.id.tv_namepoin);
            btnBuy = itemView.findViewById(R.id.btn_buy);
        }
    }
}
