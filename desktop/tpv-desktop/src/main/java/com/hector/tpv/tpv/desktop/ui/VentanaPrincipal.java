package com.hector.tpv.tpv.desktop.ui;

import com.hector.tpv.tpv.desktop.api.ApiClient;
import com.hector.tpv.tpv.desktop.model.ComandaRequest;
import com.hector.tpv.tpv.desktop.model.Mesa;
import com.hector.tpv.tpv.desktop.model.Producto;
import com.hector.tpv.tpv.desktop.model.MesaAbierta;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private final ApiClient api = ApiClient.fromProps();
    private final DefaultListModel<Mesa> modeloMesas = new DefaultListModel<>();
    private final DefaultListModel<Producto> modeloProductos = new DefaultListModel<>();
    private final ModeloTablaCarrito modeloCarrito = new ModeloTablaCarrito();
    private final ModeloTablaAbierto modeloAbierto = new ModeloTablaAbierto();

    private final JList<Mesa> listaMesas = new JList<>(modeloMesas);
    private final JList<Producto> listaProductos = new JList<>(modeloProductos);
    private final JTable tablaCarrito = new JTable(modeloCarrito);
    private final JTable tablaAbierto = new JTable(modeloAbierto);

    private final JLabel lblTotal = new JLabel("Total: 0.00€");
    private final JLabel lblTotalAbierto = new JLabel("Abierto: 0.00€");

    private final JButton btnAgregar = new JButton("Añadir producto");
    private final JButton btnEnviar = new JButton("Enviar comanda");

    private Mesa mesaActual = null;

    public VentanaPrincipal() {
        setTitle("TPV Desktop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 630);
        setLocationRelativeTo(null);

        var spMesas = new JScrollPane(listaMesas);
        var spProductos = new JScrollPane(listaProductos);
        var spCarrito = new JScrollPane(tablaCarrito);
        var spAbierto = new JScrollPane(tablaAbierto);

        var pnlIzq = new JPanel(new BorderLayout());
        pnlIzq.add(new JLabel("Mesas"), BorderLayout.NORTH);
        pnlIzq.add(spMesas, BorderLayout.CENTER);

        var pnlCentro = new JPanel(new BorderLayout());
        pnlCentro.add(new JLabel("Productos"), BorderLayout.NORTH);
        pnlCentro.add(spProductos, BorderLayout.CENTER);

        var pnlAbierto = new JPanel(new BorderLayout());
        pnlAbierto.add(new JLabel("Abierto"), BorderLayout.NORTH);
        pnlAbierto.add(spAbierto, BorderLayout.CENTER);
        pnlAbierto.add(lblTotalAbierto, BorderLayout.SOUTH);

        var pnlCarrito = new JPanel(new BorderLayout());
        pnlCarrito.add(new JLabel("Carrito (nuevo)"), BorderLayout.NORTH);
        pnlCarrito.add(spCarrito, BorderLayout.CENTER);

        var contDer = new JPanel();
        contDer.setLayout(new BoxLayout(contDer, BoxLayout.Y_AXIS));
        pnlAbierto.setPreferredSize(new Dimension(380, 250));
        pnlCarrito.setPreferredSize(new Dimension(380, 280));
        contDer.add(pnlAbierto);
        contDer.add(Box.createVerticalStrut(8));
        contDer.add(pnlCarrito);

        var pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotones.add(lblTotal);
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnEnviar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlIzq, BorderLayout.WEST);
        getContentPane().add(pnlCentro, BorderLayout.CENTER);
        getContentPane().add(contDer, BorderLayout.EAST);
        getContentPane().add(pnlBotones, BorderLayout.SOUTH);

        listaMesas.setFixedCellWidth(140);
        listaProductos.setFixedCellWidth(300);
        tablaAbierto.setFillsViewportHeight(true);
        tablaCarrito.setFillsViewportHeight(true);

        listaMesas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mesaActual = listaMesas.getSelectedValue();
                modeloCarrito.getLineas().clear();
                modeloCarrito.fireTableDataChanged();
                lblTotal.setText("Total: 0.00€");
                cargarAbierto();
            }
        });

        btnAgregar.addActionListener(e -> {
            var p = listaProductos.getSelectedValue();
            if (p != null) {
                modeloCarrito.addProducto(p);
                lblTotal.setText(String.format("Total: %.2f€", modeloCarrito.getTotal()));
            }
        });

        btnEnviar.addActionListener(e -> enviarComanda());

        SwingUtilities.invokeLater(() -> {
            cargarMesas();
            cargarProductos();
        });
    }

    private void cargarMesas() {
        try {
            List<Mesa> m = api.getMesas();
            modeloMesas.clear();
            for (Mesa x : m) {
                modeloMesas.addElement(x);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando mesas: " + ex.getMessage());
        }
    }

    private void cargarProductos() {
        try {
            List<Producto> p = api.getProductos();
            modeloProductos.clear();
            for (Producto x : p) {
                modeloProductos.addElement(x);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + ex.getMessage());
        }
    }

    private void cargarAbierto() {
        if (mesaActual == null) {
            modeloAbierto.setDatos(java.util.List.of());
            lblTotalAbierto.setText("Abierto: 0.00€");
            return;
        }
        try {
            MesaAbierta abierto = api.getMesaAbierta(mesaActual.getId());
            modeloAbierto.setDatos(abierto.getItems());
            lblTotalAbierto.setText(String.format("Abierto: %.2f€", abierto.getTotal()));
        } catch (Exception ex) {
            modeloAbierto.setDatos(java.util.List.of());
            lblTotalAbierto.setText("Abierto: 0.00€");
            JOptionPane.showMessageDialog(this, "Error cargando abierto: " + ex.getMessage());
        }
    }

    private void enviarComanda() {
        if (mesaActual == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa primero");
            return;
        }
        if (modeloCarrito.getLineas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en la comanda");
            return;
        }
        try {
            var c = new ComandaRequest();
            c.setMesaId(mesaActual.getId());
            c.setCamareroId(1L);
            c.setLineas(modeloCarrito.getLineas());
            var id = api.postComanda(c);
            JOptionPane.showMessageDialog(this, id.isPresent() ? "Comanda enviada. ID=" + id.get() : "Comanda enviada.");
            modeloCarrito.getLineas().clear();
            modeloCarrito.fireTableDataChanged();
            lblTotal.setText("Total: 0.00€");
            cargarAbierto();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error enviando comanda: " + ex.getMessage());
        }
    }
}
