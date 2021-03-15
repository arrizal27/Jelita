package com.smkn4bdg.jelita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerPengepulAdapter extends BaseAdapter {
    private String[] pengepul;
    private String[] noTelp;
    private String[] alamat;
    private LayoutInflater inflater;

    SpinnerPengepulAdapter(Context context, String[] pengepul, String[] noTelp, String[] alamat) {
        this.pengepul = pengepul;
        this.noTelp = noTelp;
        this.alamat = alamat;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return pengepul.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.card_dropdown_pengepul, null);
        TextView namaPengepul = view.findViewById(R.id.nama_pengepul);
        TextView notelpPengepul = view.findViewById(R.id.no_telp_pengepul);
        TextView alamatPengepul = view.findViewById(R.id.alamat_pengepul);

        namaPengepul.setText(pengepul[i]);
        notelpPengepul.setText(noTelp[i]);
        alamatPengepul.setText(alamat[i]);

        return view;
    }
}
