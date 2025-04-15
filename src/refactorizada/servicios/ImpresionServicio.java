package refactorizada.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JTextArea;
import refactorizada.dominio.FacturaConfiguracion;
import refactorizada.dominio.Producto;

/**
 * Servicio para generar y formatear la representación visual de facturas.
 * <p>
 * Gestiona la creación de plantillas de facturas con formato estructurado,
 * incluyendo cálculos detallados y presentación en componentes de interfaz gráfica.
 * </p>
 * 
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @since 2025
 * @see JTextArea
 * @see BigDecimal
 */
public class ImpresionServicio {

    /**
     * Genera y muestra una factura detallada en un componente de texto.
     * <p>
     * El formato incluye:
     * <ol>
     *   <li>Encabezado con título</li>
     *   <li>Listado de productos con subtotales</li>
     *   <li>Desglose de descuentos, impuestos y envío</li>
     *   <li>Total final con formato monetario</li>
     * </ol>
     * Todos los valores se redondean a 2 decimales usando {@link RoundingMode#HALF_UP}.
     * </p>
     * 
     * @param productos       Lista de productos a facturar (no nula)
     * @param subtotal        Subtotal calculado de los productos
     * @param config          Configuración con porcentajes aplicables
     * @param totalFinal      Valor final ya calculado
     * @param areaResultado   Componente gráfico donde se mostrará la factura
     * @throws IllegalArgumentException Si algún parámetro es nulo
     * @see FacturaConfiguracion
     * @see BigDecimal#setScale(int, RoundingMode)
     */
    public void imprimirFactura(List<Producto> productos, BigDecimal subtotal,
                              FacturaConfiguracion config, BigDecimal totalFinal,
                              JTextArea areaResultado) {
        
        // Encabezado de la factura en el área de texto
        areaResultado.append("\n--- FACTURA ---\n");
        
        // Detalles de productos
        productos.forEach(p -> 
            areaResultado.append(
                String.format("%s x%d = %.2f\n", 
                    p.getNombre(), 
                    p.getCantidad(), 
                    p.getSubtotal())
            )
        );

        // Cálculos para mostrar detalles
        BigDecimal descuento = subtotal.multiply(config.getDescuento())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal impuestos = subtotal.subtract(descuento)
                                     .multiply(config.getImpuesto())
                                     .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Impresión de valores en el área de texto
        areaResultado.append(String.format("Subtotal: %.2f\n", subtotal));
        areaResultado.append(String.format("Descuento (%.2f%%): -%.2f\n", config.getDescuento(), descuento));
        areaResultado.append(String.format("Impuestos (%.2f%%): +%.2f\n", config.getImpuesto(), impuestos));
        areaResultado.append(String.format("Envío: +%.2f\n", config.getEnvio()));
        areaResultado.append(String.format("TOTAL FINAL: %.2f\n", totalFinal));
    }
}
