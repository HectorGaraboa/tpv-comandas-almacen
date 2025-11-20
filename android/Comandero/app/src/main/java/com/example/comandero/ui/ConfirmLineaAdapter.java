package com.example.comandero.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandero.R;
import com.example.comandero.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfirmLineaAdapter extends RecyclerView.Adapter<ConfirmLineaAdapter.VH> {

    public interface OnLineaLongClick {
        void onLongClick(long productoId);
    }

    private final List<Long> ids = new ArrayList<>();
    private final Map<Long, Integer> cantidades;
    private final Map<Long, Producto> productos;
    private final OnLineaLongClick listener;

    public ConfirmLineaAdapter(Map<Long, Integer> cantidades, Map<Long, Producto> productos, OnLineaLongClick listener) {
        this.cantidades = cantidades;
        this.productos = productos;
        this.listener = listener;
        ids.addAll(cantidades.keySet());
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_linea, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        long id = ids.get(position);
        Producto p = productos.get(id);
        int q = cantidades.get(id);
        h.tvNombre.setText(p != null ? p.getNombre() : ("ID " + id));
        h.tvCantidad.setText("x" + q);
        h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) listener.onLongClick(id);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public void refresh() {
        ids.clear();
        ids.addAll(cantidades.keySet());
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvCantidad;
        VH(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreLinea);
            tvCantidad = itemView.findViewById(R.id.tvCantidadLinea);
        }
    }
}
