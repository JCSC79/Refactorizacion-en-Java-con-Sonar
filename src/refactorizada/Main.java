package refactorizada;

import refactorizada.gui.FacturaFrame;

public class Main {
    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos
        javax.swing.SwingUtilities.invokeLater(() -> {
            FacturaFrame ventana = new FacturaFrame();
            ventana.setVisible(true);
        });
    }
}
