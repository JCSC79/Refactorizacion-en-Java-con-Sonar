package refactorizada.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import refactorizada.dominio.FacturaConfiguracion;
import refactorizada.dominio.Producto;

public class FacturadorServicio {
    
    public BigDecimal calcularSubtotal(List<Producto> productos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto p : productos) {
            total = total.add(p.getSubtotal());
        }
        return total;
    }

    public BigDecimal calcularTotalFinal(BigDecimal subtotal, FacturaConfiguracion config) {
        BigDecimal descuento = subtotal.multiply(config.getDescuento())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal totalConDescuento = subtotal.subtract(descuento);

        BigDecimal impuestos = totalConDescuento.multiply(config.getImpuesto())
                                             .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return totalConDescuento.add(impuestos).add(config.getEnvio());
    }
}
