package com.example.comandero.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.comandero.R;
import com.example.comandero.model.MesaAbiertaItem;
import java.util.ArrayList;
import java.util.List;

public class ComandaAbiertaAdapter extends RecyclerView.Adapter<ComandaAbiertaAdapter.VH> {

    private final List<MesaAbiertaItem> datos = new ArrayList<>();

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comanda_abierta, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        MesaAbiertaItem x = datos.get(position);
        h.txtNombre.setText(x.getNombre());
        h.txtCantidad.setText(String.valueOf(x.getCantidad()));
        h.txtPrecio.setText(String.format("%.2f €", x.getPrecioUnitario()));
        h.txtSubtotal.setText(String.format("%.2f €", x.getSubtotal()));
    }

    @Override
    public int getItemCount() { return datos.size(); }

    public void setItems(List<MesaAbiertaItem> items) {
        datos.clear();
        if (items != null) datos.addAll(items);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtNombre, txtCantidad, txtPrecio, txtSubtotal;
        VH(View v) {
            super(v);
            txtNombre = v.findViewById(R.id.txtNombreProducto);
            txtCantidad = v.findViewById(R.id.txtCantidad);
            txtPrecio = v.findViewById(R.id.txtPrecioUnit);
            txtSubtotal = v.findViewById(R.id.txtSubtotal);
        }
    }
}
