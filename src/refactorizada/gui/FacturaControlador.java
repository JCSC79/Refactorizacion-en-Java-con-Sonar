package refactorizada.gui;

import java.math.BigDecimal;
import javax.swing.JOptionPane;
import refactorizada.dominio.FacturaConfiguracion;

public class FacturaControlador {

    public FacturaConfiguracion obtenerConfiguracion() {
        try {
            String descuento = JOptionPane.showInputDialog("Porcentaje de descuento (%):");
            String impuesto = JOptionPane.showInputDialog("Porcentaje de impuestos (%):");
            String envio = JOptionPane.showInputDialog("Costo de envío:");

            return new FacturaConfiguracion(
                new BigDecimal(descuento),
                new BigDecimal(impuesto),
                new BigDecimal(envio)
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valores inválidos. Usando configuración por defecto.");
            return new FacturaConfiguracion(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }
    }
}