package refactorizada;

import refactorizada.gui.FacturaFrame;

/**
 * Clase principal que inicia la aplicación de facturación.
 * <p>
 * Crea y muestra la ventana principal de la aplicación.
 * </p>
 *
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @since 2025
 * @see refactorizada.gui.FacturaFrame
 */
public class Main {

  /**
   * Método principal que ejecuta la aplicación.
   * <p>
   * Crea una instancia de {@link FacturaFrame} y la hace visible en el hilo de eventos de Swing.
   * </p>
   *
   * @param args Argumentos de la línea de comandos (no se utilizan)
   */
  public static void main(String[] args) {
    // Ejecutar la interfaz gráfica en el hilo de eventos
    javax.swing.SwingUtilities.invokeLater(
        () -> {
          FacturaFrame ventana = new FacturaFrame();
          ventana.setVisible(true);
        });
  }
}
