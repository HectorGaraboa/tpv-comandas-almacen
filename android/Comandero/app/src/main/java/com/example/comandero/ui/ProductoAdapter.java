package com.example.comandero.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandero.R;
import com.example.comandero.model.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProdVH> {

    public interface OnProductoClick { void onClick(Producto p); }

    private final List<Producto> datos;
    private final OnProductoClick listener;

    public ProductoAdapter(List<Producto> datos, OnProductoClick listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProdVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdVH h, int position) {
        final Producto p = datos.get(position);
        h.tvNombre.setText(p.getNombre());
        h.tvPrecio.setText(String.format("%.2f €", p.getPrecioVenta()));
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { if (listener != null) listener.onClick(p); }
        });
    }

    @Override
    public int getItemCount() { return datos != null ? datos.size() : 0; }

    static class ProdVH extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvPrecio;
        ProdVH(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
        }
    }
}
