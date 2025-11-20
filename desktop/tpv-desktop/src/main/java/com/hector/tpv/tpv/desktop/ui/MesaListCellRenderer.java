package com.hector.tpv.tpv.desktop.ui;

import com.hector.tpv.tpv.desktop.model.Mesa;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class MesaListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Mesa) {
            Mesa m = (Mesa) value;
            setText(m.getCodigo());

            if (!isSelected) {
                if (m.isOcupada()) {
                    setBackground(new Color(0x90, 0xEE, 0x90)); //verde
                    setForeground(Color.BLACK);
                } else {
                    setBackground(new Color(0xDD, 0xDD, 0xDD)); // gris claro
                    setForeground(Color.BLACK);
                }
            }
        }

        setOpaque(true);
        return this;
    }
}
