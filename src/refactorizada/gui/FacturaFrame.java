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

/**
 * Clase principal de la interfaz gráfica para generar facturas.
 * <p>
 * Permite agregar productos, calcular totales y generar facturas
 * con impuestos configurables. Incluye soporte para temas claros/oscuros.
 * </p>
 *
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @see FacturadorServicio
 * @see Producto
 */
public class FacturaFrame extends JFrame {

  /** Campo de texto para el nombre del producto. */
  private JTextField txtNombre;

  /** Campo de texto para la cantidad del producto. */
  private JTextField txtCantidad;

  /** Campo de texto para el precio del producto. */
  private JTextField txtPrecio;

  /** Botón para agregar un producto a la factura. */
  private JButton btnAgregar;

  /** Botón para calcular el total de la factura. */
  private JButton btnCalcular;

  /** Botón para limpiar todos los campos y la factura. */
  private JButton btnLimpiar;

  /** Área de texto para mostrar los resultados de la factura. */
  private JTextArea areaResultado;

  /** Checkbox para activar o desactivar el tema oscuro. */
  private JCheckBox chkTemaOscuro;

  /** Servicio para realizar cálculos de facturación. */
  @SuppressWarnings("FieldMayBeFinal")
  private FacturadorServicio facturador = new FacturadorServicio();

  /** Lista de productos en la factura actual. */
  @SuppressWarnings("FieldMayBeFinal")
  private List<Producto> productos = new ArrayList<>();

  /** Indica si el tema oscuro está activado o no. */
  private boolean temaOscuro = false;

  /** Constructor de la clase FacturaFrame. */
  @SuppressWarnings("CallToPrintStackTrace")
  public FacturaFrame() {
    configurarVentana();
    crearComponentes();
    agregarListeners();
    try {
      java.net.URL imgURL = getClass().getClassLoader().getResource("recursos/icono.png");
      if (imgURL != null) {
          setIconImage(new ImageIcon(imgURL).getImage());
      } else {
          System.err.println("No se encontró el archivo de icono");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Configura las propiedades de la ventana principal. */
  private void configurarVentana() {
    setTitle("Generador de Facturas");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    setLayout(new BorderLayout());
  }

  /** Crea y agrega los componentes a la ventana. */
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
    btnLimpiar = crearBotonEstilizado("🧹 Limpiar Todo", new Color(52, 152, 219)); // Azul

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

  /**
   * Crea una etiqueta con el estilo predefinido.
   *
   * @param texto Texto de la etiqueta
   * @return JLabel con el estilo aplicado
   */
  private JLabel crearLabelEstilizado(String texto) {
    JLabel label = new JLabel(texto);
    label.setFont(new Font("Segoe UI", Font.BOLD, 14));
    return label;
  }

  /**
   * Crea un borde estilizado para los campos de texto.
   *
   * @return Border con el estilo aplicado
   */
  private Border crearBordeEstilizado() {
    return BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 5, 5, 5));
  }

  /**
   * Crea un botón con el estilo predefinido.
   *
   * @param texto Texto del botón
   * @param color Color de fondo del botón
   * @return JButton con el estilo aplicado
   */
  private JButton crearBotonEstilizado(String texto, Color color) {
    JButton boton = new JButton(texto);
    boton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
    boton.setBackground(color);
    boton.setForeground(Color.WHITE);
    boton.setFocusPainted(false);
    boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    return boton;
  }

  /** Agrega los listeners a los componentes. */
  @SuppressWarnings("unused")
  private void agregarListeners() {
    btnAgregar.addActionListener(e -> agregarProducto());
    btnCalcular.addActionListener(e -> generarFactura());
    btnLimpiar.addActionListener(e -> limpiarTodo());
    chkTemaOscuro.addActionListener(e -> cambiarTema());
  }

  /**
   * Agrega un producto a la lista de la factura actual.
   *
   *
   * @throws NumberFormatException Si la cantidad o precio no son numéricos
   * @see Producto#Producto(String, int, BigDecimal)
   */
  private void agregarProducto() {
    try {
      String nombre = capitalizarPrimeraLetra(txtNombre.getText().trim());
      int cantidad = Integer.parseInt(txtCantidad.getText());
      BigDecimal precio = new BigDecimal(txtPrecio.getText());

      productos.add(new Producto(nombre, cantidad, precio));
      areaResultado.append("✔ Añadido: " + nombre + " x" + cantidad + "\n");

      limpiarCampos();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(
          this, "Error en los datos", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /** Genera la factura con los productos agregados. */
  private void generarFactura() {
    FacturaControlador controlador = new FacturaControlador();
    FacturaConfiguracion config = controlador.obtenerConfiguracion();

    BigDecimal subtotal = facturador.calcularSubtotal(productos);
    BigDecimal totalFinal = facturador.calcularTotalFinal(subtotal, config);

    new ImpresionServicio().imprimirFactura(productos, subtotal, config, totalFinal, areaResultado);
  }

  /** Limpia todos los campos y la lista de productos. */
  private void limpiarTodo() {
    productos.clear();
    areaResultado.setText("");
    limpiarCampos();
    JOptionPane.showMessageDialog(
        this,
        "Todos los productos fueron eliminados",
        "Limpiar",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /** Cambia el tema de la aplicación entre claro y oscuro. */
  private void cambiarTema() {
    temaOscuro = !temaOscuro;
    aplicarTema();
  }

  /**
   * Oscurece un color dado.
   *
   * @param colorOriginal Color a oscurecer
   * @return Color oscurecido
   */
  private Color oscurecerColor(Color colorOriginal) {
    int r = Math.max(colorOriginal.getRed() - 30, 0);
    int g = Math.max(colorOriginal.getGreen() - 30, 0);
    int b = Math.max(colorOriginal.getBlue() - 30, 0);
    return new Color(r, g, b);
  }

  /** Aplica el tema de color a la interfaz. */
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

  /**
   * Aplica el tema de color a un componente y sus hijos de forma recursiva.
   *
   * @param comp Componente a aplicar el tema
   * @param bg Color de fondo
   * @param fg Color de texto
   * @param compBg Color de fondo de los componentes
   * @param bordeColor Color de los bordes
   */
  private void aplicarTemaRecursivo(
      Component comp, Color bg, Color fg, Color compBg, Color bordeColor) {
    if (comp instanceof JPanel jPanel) {
      comp.setBackground(bg);
      for (Component child : jPanel.getComponents()) {
        aplicarTemaRecursivo(child, bg, fg, compBg, bordeColor);
      }
    } else if (comp instanceof JLabel || comp instanceof JCheckBox) {
      comp.setForeground(fg);
      comp.setBackground(bg);
    } else if (comp instanceof JTextField txt) {
      txt.setForeground(fg);
      txt.setBackground(compBg);
      txt.setCaretColor(fg);
      txt.setBorder(
          BorderFactory.createCompoundBorder(
              BorderFactory.createLineBorder(bordeColor),
              BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    } else if (comp instanceof JButton btn) {
      // Mantener
      btn.setForeground(Color.WHITE);
      if (!temaOscuro) {
        btn.setBackground(((JButton) comp).getBackground()); // Color original
      } else {
        btn.setBackground(oscurecerColor(btn.getBackground())); // Versión oscura del color
      }
    } else if (comp instanceof JScrollPane jScrollPane) {
      comp.setBackground(bg);
      JViewport viewport = jScrollPane.getViewport();
      if (viewport != null) {
        Component view = viewport.getView();
        if (view != null) {
          aplicarTemaRecursivo(view, bg, fg, compBg, bordeColor);
        }
      }
    }
  }

  /**
   * Capitaliza la primera letra de un texto.
   *
   * @param texto Texto a capitalizar
   * @return Texto con la primera letra en mayúscula
   */
  private String capitalizarPrimeraLetra(String texto) {
    if (texto == null || texto.isEmpty()) return texto;
    return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
  }

  /** Limpia los campos de entrada de texto. */
  private void limpiarCampos() {
    txtNombre.setText("");
    txtCantidad.setText("");
    txtPrecio.setText("");
    txtNombre.requestFocus();
  }
}
