/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpv.desktop.ui;

import com.hector.tpv.tpv.desktop.model.LineaComanda;
import com.hector.tpv.tpv.desktop.model.Producto;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
public class ModeloTablaCarrito extends AbstractTableModel {
    private final List<LineaComanda> lineas=new ArrayList<>();
    private final List<Producto> productos=new ArrayList<>();
    private final String[] cols={"Producto","Cantidad","Precio","IVA","Subtotal"};
    public void addProducto(Producto p){
        for(int i=0;i<lineas.size();i++){
            if(lineas.get(i).getProductoId().equals(p.getId())){
                var l=lineas.get(i);
                l.setCantidad(l.getCantidad()+1);
                fireTableRowsUpdated(i,i);
                return;
            }
        }
        var l=new LineaComanda();
        l.setProductoId(p.getId());
        l.setCantidad(1);
        l.setTextoModificador("");
        l.setPrecioUnitario(p.getPrecioVenta());
        l.setIvaTipo(p.getIvaTipo());
        lineas.add(l);
        productos.add(p);
        fireTableRowsInserted(lineas.size()-1,lineas.size()-1);
    }
    public List<LineaComanda> getLineas(){return lineas;}
    public double getTotal(){
        double t=0;
        for(LineaComanda l:lineas)t+=l.getCantidad()*l.getPrecioUnitario();
        return t;
    }
    @Override public int getRowCount(){return lineas.size();}
    @Override public int getColumnCount(){return cols.length;}
    @Override public String getColumnName(int c){return cols[c];}
    @Override public Object getValueAt(int r,int c){
        var l=lineas.get(r);
        if(c==0)return productos.get(r).getNombre();
        if(c==1)return l.getCantidad();
        if(c==2)return String.format("%.2f€",l.getPrecioUnitario());
        if(c==3)return l.getIvaTipo()+"%";
        if(c==4)return String.format("%.2f€",l.getCantidad()*l.getPrecioUnitario());
        return "";
    }
}