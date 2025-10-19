package com.hector.tpv.tpv.desktop;
import com.hector.tpv.tpv.desktop.ui.VentanaPrincipal;
import javax.swing.SwingUtilities;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
