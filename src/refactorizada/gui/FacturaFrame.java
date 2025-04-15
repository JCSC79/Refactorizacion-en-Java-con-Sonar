package refactorizada.gui;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import refactorizada.dominio.FacturaConfiguracion;
import refactorizada.dominio.Producto;
import refactorizada.servicios.FacturadorServicio;
import refactorizada.servicios.ImpresionServicio;

public class FacturaFrame extends JFrame {
    private JTextField txtNombre, txtCantidad, txtPrecio;
    private JButton btnAgregar, btnCalcular;
    private JTextArea areaResultado;
    private FacturadorServicio facturador = new FacturadorServicio();
    private List<Producto> productos = new ArrayList<>();

    public FacturaFrame() {
        configurarVentana();
        crearComponentes();
        agregarListeners();
    }

    private void configurarVentana() {
        setTitle("Generador de Facturas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(4, 2, 10, 10));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEntrada.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelEntrada.add(txtNombre);

        panelEntrada.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelEntrada.add(txtCantidad);

        panelEntrada.add(new JLabel("Precio Unitario:"));
        txtPrecio = new JTextField();
        panelEntrada.add(txtPrecio);

        btnAgregar = new JButton("Agregar Producto");
        panelEntrada.add(btnAgregar);

        add(panelEntrada, BorderLayout.NORTH);

        // Área de resultados
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        // Botón final
        btnCalcular = new JButton("Generar Factura");
        add(btnCalcular, BorderLayout.SOUTH);
    }

    private void agregarListeners() {
        btnAgregar.addActionListener(e -> agregarProducto());
        btnCalcular.addActionListener(e -> generarFactura());
    }

    private void agregarProducto() {
        try {
            String nombre = txtNombre.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            BigDecimal precio = new BigDecimal(txtPrecio.getText());
            
            productos.add(new Producto(nombre, cantidad, precio));
            areaResultado.append("✔ Añadido: " + nombre + " x" + cantidad + "\n");
            
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarFactura() {
        FacturaControlador controlador = new FacturaControlador();
        FacturaConfiguracion config = controlador.obtenerConfiguracion();
        
        BigDecimal subtotal = facturador.calcularSubtotal(productos);
        BigDecimal totalFinal = facturador.calcularTotalFinal(subtotal, config);
        
        mostrarFacturaEnPantalla(subtotal, totalFinal, config);
    }

    private void mostrarFacturaEnPantalla(BigDecimal subtotal, BigDecimal totalFinal, FacturaConfiguracion config) {
        areaResultado.append("\n--- FACTURA FINAL ---\n");
        //new ImpresionServicio().imprimirFactura(productos, subtotal, config, totalFinal);
        // Línea corregida en FacturaFrame.java
        new ImpresionServicio().imprimirFactura(productos, subtotal, config, totalFinal, areaResultado);

    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtNombre.requestFocus();
    }
}
