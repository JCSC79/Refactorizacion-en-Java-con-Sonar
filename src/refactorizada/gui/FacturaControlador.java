package refactorizada.gui;

import java.math.BigDecimal;
import javax.swing.JOptionPane;
import refactorizada.dominio.FacturaConfiguracion;

/**
 * Controlador para la gestión de la configuración de facturación.
 * <p>
 * Gestiona la interacción con el usuario para obtener parámetros de configuración
 * (descuentos, impuestos y costos de envío) a través de cuadros de diálogo.
 * </p>
 * 
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @since 2025
 * @see FacturaConfiguracion
 * @see JOptionPane
 */
public class FacturaControlador {

    /**
     * Solicita al usuario los parámetros de configuración mediante diálogos.
     * <p>
     * Muestra tres cuadros de diálogo para obtener:
     * <ol>
     *   <li>Porcentaje de descuento (como valor decimal)</li>
     *   <li>Porcentaje de impuesto (como valor decimal)</li>
     *   <li>Costo de envío (valor numérico)</li>
     * </ol>
     * Si ocurre un error en la conversión, usa valores por defecto (0, 0, 0).
     * </p>
     * 
     * @return Instancia de {@link FacturaConfiguracion} con los valores proporcionados
     * @throws NumberFormatException Si los valores ingresados no son numéricos válidos
     * @see BigDecimal#BigDecimal(String)
     */
    @SuppressWarnings("UseSpecificCatch")
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
