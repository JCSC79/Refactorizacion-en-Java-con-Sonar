package refactorizada.gui;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import refactorizada.dominio.*;
import refactorizada.servicios.*;

public class FacturaFrame extends JFrame {
    private JTextField txtNombre, txtCantidad, txtPrecio;
    private JButton btnAgregar, btnCalcular, btnLimpiar;
    private JTextArea areaResultado;
    private JCheckBox chkTemaOscuro;
    private FacturadorServicio facturador = new FacturadorServicio();
    private List<Producto> productos = new ArrayList<>();
    private boolean temaOscuro = false;

    public FacturaFrame() {
        configurarVentana();
        crearComponentes();
        agregarListeners();
    }

    private void configurarVentana() {
        setTitle("Generador de Facturas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel de entrada con nuevos estilos
        JPanel panelEntrada = new JPanel(new GridLayout(5, 2, 15, 15));
        panelEntrada.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelEntrada.setBackground(new Color(245, 245, 245));

        // Fuentes personalizadas
        Font fuenteLabel = new Font("Segoe UI Emoji", Font.BOLD, 14);
        Font fuenteTexto = new Font("Segoe UI ", Font.PLAIN, 14);

        // Campo: Nombre
        JLabel lblNombre = new JLabel("Nombre del Producto:");
        lblNombre.setFont(fuenteLabel);
        panelEntrada.add(lblNombre);
        
        txtNombre = new JTextField();
        txtNombre.setFont(fuenteTexto);
        txtNombre.setBorder(crearBordeEstilizado());
        panelEntrada.add(txtNombre);

        // Campo: Cantidad
        panelEntrada.add(crearLabelEstilizado("Cantidad:"));
        txtCantidad = new JTextField();
        txtCantidad.setFont(fuenteTexto);
        txtCantidad.setBorder(crearBordeEstilizado());
        panelEntrada.add(txtCantidad);

        // Campo: Precio
        panelEntrada.add(crearLabelEstilizado("Precio Unitario:"));
        txtPrecio = new JTextField();
        txtPrecio.setFont(fuenteTexto);
        txtPrecio.setBorder(crearBordeEstilizado());
        panelEntrada.add(txtPrecio);

        // Botones en panel separado
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelBotones.setBackground(new Color(245, 245, 245));

        btnAgregar = crearBotonEstilizado("➕ Agregar Producto", new Color(46, 204, 113)); // Verde
        btnLimpiar = crearBotonEstilizado("🧹 Limpiar Todo", new Color(52, 152, 219));    // Azul
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);
        
        // Panel principal de entrada
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelEntrada, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        // Área de resultados mejorada
        areaResultado = new JTextArea();
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaResultado.setBorder(new EmptyBorder(15, 20, 15, 20));
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(scroll, BorderLayout.CENTER);

        // Botón final estilizado
        btnCalcular = crearBotonEstilizado("💳 Generar Factura", new Color(155, 89, 182)); // Morado
        add(btnCalcular, BorderLayout.SOUTH);

        // Checkbox de tema oscuro mejorado
        chkTemaOscuro = new JCheckBox("🌙 Tema Oscuro");
        chkTemaOscuro.setFont(fuenteLabel);
        chkTemaOscuro.setBackground(new Color(245, 245, 245));
        panelEntrada.add(new JLabel(""));
        panelEntrada.add(chkTemaOscuro);
    }

    private JLabel crearLabelEstilizado(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private Border crearBordeEstilizado() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return boton;
    }

    private void agregarListeners() {
        btnAgregar.addActionListener(e -> agregarProducto());
        btnCalcular.addActionListener(e -> generarFactura());
        btnLimpiar.addActionListener(e -> limpiarTodo());
        chkTemaOscuro.addActionListener(e -> cambiarTema());
    }

    private void agregarProducto() {
        try {
            String nombre = capitalizarPrimeraLetra(txtNombre.getText().trim());
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
        
        new ImpresionServicio().imprimirFactura(productos, subtotal, config, totalFinal, areaResultado);
    }

    private void limpiarTodo() {
        productos.clear();
        areaResultado.setText("");
        limpiarCampos();
        JOptionPane.showMessageDialog(this, 
            "Todos los productos fueron eliminados", 
            "Limpiar", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarTema() {
        temaOscuro = !temaOscuro;
        aplicarTema();
    }

    private Color oscurecerColor(Color colorOriginal) {
        int r = Math.max(colorOriginal.getRed() - 30, 0);
        int g = Math.max(colorOriginal.getGreen() - 30, 0);
        int b = Math.max(colorOriginal.getBlue() - 30, 0);
        return new Color(r, g, b);
    }
    private void aplicarTema() {
        // Colores para tema oscuro
        Color colorFondoOscuro = new Color(45, 45, 45);
        Color colorTextoOscuro = new Color(220, 220, 220);
        Color colorComponentesOscuro = new Color(60, 60, 60);
        Color colorBordeOscuro = new Color(100, 100, 100);

        // Colores para tema claro
        Color colorFondoClaro = new Color(245, 245, 245);
        Color colorTextoClaro = Color.BLACK;
        Color colorComponentesClaro = Color.WHITE;
        Color colorBordeClaro = new Color(200, 200, 200);

        // Aplicar colores según el tema
        Color colorFondo = temaOscuro ? colorFondoOscuro : colorFondoClaro;
        Color colorTexto = temaOscuro ? colorTextoOscuro : colorTextoClaro;
        Color colorComponentes = temaOscuro ? colorComponentesOscuro : colorComponentesClaro;
        Color colorBorde = temaOscuro ? colorBordeOscuro : colorBordeClaro;

        // Aplicar a toda la ventana
        getContentPane().setBackground(colorFondo);

        // Recorrer todos los componentes
        aplicarTemaRecursivo(this.getContentPane(), colorFondo, colorTexto, colorComponentes, colorBorde);

        // Área de resultados
        areaResultado.setBackground(temaOscuro ? new Color(30, 30, 30) : Color.WHITE);
        areaResultado.setForeground(colorTexto);
    }

    private void aplicarTemaRecursivo(Component comp, Color bg, Color fg, Color compBg, Color bordeColor) {
        if (comp instanceof JPanel) {
            comp.setBackground(bg);
            for (Component child : ((JPanel) comp).getComponents()) {
                aplicarTemaRecursivo(child, bg, fg, compBg, bordeColor);
            }
        } else if (comp instanceof JLabel || comp instanceof JCheckBox) {
            comp.setForeground(fg);
            comp.setBackground(bg);
        } else if (comp instanceof JTextField) {
            JTextField txt = (JTextField) comp;
            txt.setForeground(fg);
            txt.setBackground(compBg);
            txt.setCaretColor(fg);
            txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bordeColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } else if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            // Mantener
            btn.setForeground(Color.WHITE);
            if (!temaOscuro) {
                btn.setBackground(((JButton) comp).getBackground()); // Color original
            } else {
                btn.setBackground(oscurecerColor(btn.getBackground())); // Versión oscura del color
            }
        } else if (comp instanceof JScrollPane) {
            comp.setBackground(bg);
            JViewport viewport = ((JScrollPane) comp).getViewport();
            if (viewport != null) {
                Component view = viewport.getView();
                if (view != null) {
                    aplicarTemaRecursivo(view, bg, fg, compBg, bordeColor);
                }
            }
        }
    }

    private String capitalizarPrimeraLetra(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtNombre.requestFocus();
    }
}
