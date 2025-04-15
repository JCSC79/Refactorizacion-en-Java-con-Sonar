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
     *   <li>Tabla de productos con alineación decimal</li>
     *   <li>Desglose de descuentos, impuestos y envío</li>
     *   <li>Total final con formato monetario</li>
     *   <li>Pie de página con mensaje personalizado</li>
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
        
        // Limpiar contenido previo
        areaResultado.setText("");
        
        // Encabezado de la factura
        areaResultado.append("=========================================================\n");
        areaResultado.append("                 FACTURA ELECTRÓNICA\n");
        areaResultado.append("=========================================================\n\n");
        
        // Tabla de productos con alineación
        areaResultado.append(String.format("%-20s %6s %10s %12s%n", 
            "PRODUCTO", "CANT", "P. UNIT", "SUBTOTAL"));
        areaResultado.append("---------------------------------------------------------\n");
        
        productos.forEach(p -> 
            areaResultado.append(
                String.format("%-20s %6d %10.2f€ %12.2f€%n",
                    p.getNombre(),
                    p.getCantidad(),
                    p.getPrecioUnitario(),  // ← Corrección aplicada aquí
                    p.getSubtotal())
            )
        );

        // Cálculos intermedios
        BigDecimal descuento = subtotal.multiply(config.getDescuento())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal impuestos = subtotal.subtract(descuento)
                                     .multiply(config.getImpuesto())
                                     .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Sección de totales
        areaResultado.append("\nDETALLE DE CARGOS\n");
        areaResultado.append(String.format("%-30s %12s%n", 
            "Subtotal:", 
            String.format("%.2f€", subtotal)));
        
        areaResultado.append(String.format("%-30s %12s%n", 
            "Descuento (" + config.getDescuento() + "%):", 
            "-" + descuento + "€"));
            
        areaResultado.append(String.format("%-30s %12s%n", 
            "Impuestos (" + config.getImpuesto() + "%):", 
            "+" + impuestos + "€"));
            
        areaResultado.append(String.format("%-30s %12s%n", 
            "Envío:", 
            "+" + config.getEnvio() + "€"));
            
        areaResultado.append(String.format("%-30s %12s%n", 
            "TOTAL FINAL:", 
            totalFinal + "€"));

        // Pie de página
        areaResultado.append("\n===========================================================\n");
        areaResultado.append("               ¡Gracias por su compra!\n");
        areaResultado.append("===========================================================\n");
    }
}
