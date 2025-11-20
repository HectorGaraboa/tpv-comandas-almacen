package com.example.comandero.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandero.R;
import com.example.comandero.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CatVH> {

    public interface OnCategoriaClick { void onClick(Categoria c); }

    private final List<Categoria> datos;
    private final OnCategoriaClick listener;

    public CategoriaAdapter(List<Categoria> datos, OnCategoriaClick listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CatVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatVH h, int position) {
        final Categoria c = datos.get(position);
        h.tv.setText(c.getNombre());
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { if (listener != null) listener.onClick(c); }
        });
    }

    @Override
    public int getItemCount() { return datos != null ? datos.size() : 0; }

    static class CatVH extends RecyclerView.ViewHolder {
        TextView tv;
        CatVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvCategoria);
        }
    }
}
