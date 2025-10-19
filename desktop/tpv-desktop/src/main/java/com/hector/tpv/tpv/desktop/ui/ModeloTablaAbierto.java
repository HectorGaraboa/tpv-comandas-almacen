package com.hector.tpv.tpv.desktop.ui;

import com.hector.tpv.tpv.desktop.model.MesaAbiertaItem;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ModeloTablaAbierto extends AbstractTableModel {

    private final String[] cols = {"Producto", "Cant.", "Precio", "IVA", "Subtotal"};
    private final List<MesaAbiertaItem> datos = new ArrayList<>();

    public void setDatos(List<MesaAbiertaItem> items) {
        datos.clear();
        if (items != null) {
            datos.addAll(items);
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    @Override
    public Object getValueAt(int r, int c) {
        var x = datos.get(r);
        if (c == 0) {
            return x.getNombre();
        }
        if (c == 1) {
            return x.getCantidad();
        }
        if (c == 2) {
            return String.format("%.2f€", x.getPrecioUnitario());
        }
        if (c == 3) {
            return x.getIvaTipo() + "%";
        }
        if (c == 4) {
            return String.format("%.2f€", x.getSubtotal());
        }
        return "";
    }
}
