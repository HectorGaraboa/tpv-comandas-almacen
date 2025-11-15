package com.example.comandero;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.ViewHolder> {

    private List<Mesa> listaMesas;

    public MesaAdapter(List<Mesa> listaMesas) {
        this.listaMesas = listaMesas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mesa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mesa mesa = listaMesas.get(position);
        holder.txtNombreMesa.setText(mesa.getCodigo());

        holder.itemView.setBackgroundColor(0xFFBDBDBD);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                Intent intent = new Intent(ctx, MesaDetalleActivity.class);
                intent.putExtra("mesaId", mesa.getId());
                intent.putExtra("mesaCodigo", mesa.getCodigo());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaMesas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreMesa;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombreMesa = itemView.findViewById(R.id.txtNombreMesa);
        }
    }
}
