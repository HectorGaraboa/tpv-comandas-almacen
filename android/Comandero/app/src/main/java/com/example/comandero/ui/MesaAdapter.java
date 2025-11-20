package com.example.comandero.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandero.R;
import com.example.comandero.model.Mesa;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.MesaVH> {

    public interface OnMesaClickListener {
        void onMesaClick(Mesa m);
    }

    private final List<Mesa> datos;
    private final OnMesaClickListener listener;

    public MesaAdapter(List<Mesa> datos, OnMesaClickListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MesaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mesa, parent, false);
        return new MesaVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaVH h, int pos) {
        final Mesa m = datos.get(pos);
        h.tvNombre.setText(m.getCodigo());
        int bg = ContextCompat.getColor(h.itemView.getContext(), m.isOcupada() ? R.color.mesa_ocupada_bg : R.color.mesa_libre_bg);
        int fg = ContextCompat.getColor(h.itemView.getContext(), m.isOcupada() ? R.color.mesa_text_contraste : R.color.mesa_text_normal);
        h.root.setBackgroundColor(bg);
        h.tvNombre.setTextColor(fg);
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onMesaClick(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos != null ? datos.size() : 0;
    }

    static class MesaVH extends RecyclerView.ViewHolder {
        LinearLayout root;
        TextView tvNombre;
        MesaVH(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.rootMesaItem);
            tvNombre = itemView.findViewById(R.id.tvNombreMesa);
        }
    }
    public void setDatos(List<Mesa> nuevas) {
        this.datos.clear();
        if (nuevas != null) {
            this.datos.addAll(nuevas);
        }
        notifyDataSetChanged();
    }

}
