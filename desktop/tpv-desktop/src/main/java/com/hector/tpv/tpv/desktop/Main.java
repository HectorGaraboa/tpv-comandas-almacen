package com.hector.tpv.tpv.desktop;

import com.formdev.flatlaf.FlatDarkLaf;
import com.hector.tpv.tpv.desktop.ui.VentanaInicial;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            UIManager.put("Component.arc", 12);
            UIManager.put("Button.arc", 12);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("ScrollBar.showButtons", true);
            UIManager.put("Table.showHorizontalLines", false);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.intercellSpacing", new java.awt.Dimension(0, 0));
        } catch (Exception e) {
            System.err.println("No se pudo aplicar FlatLaf: " + e.getMessage());
        }
        SwingUtilities.invokeLater(() -> new VentanaInicial().setVisible(true));
    }
}
