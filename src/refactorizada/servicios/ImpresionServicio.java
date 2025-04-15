package refactorizada.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;  // Import clave para la GUI
import javax.swing.JTextArea;
import refactorizada.dominio.FacturaConfiguracion;
import refactorizada.dominio.Producto;

public class ImpresionServicio {
    
    public void imprimirFactura(List<Producto> productos, BigDecimal subtotal,
                              FacturaConfiguracion config, BigDecimal totalFinal,
                              JTextArea areaResultado) {  // Nuevo parámetro para la GUI
        
        // Encabezado de la factura en el área de texto
        areaResultado.append("\n--- FACTURA ---\n");
        
        // Detalles de productos
        productos.forEach(p -> 
            areaResultado.append(
                String.format("%s x%d = %.2f\n",  // Formato corregido
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
