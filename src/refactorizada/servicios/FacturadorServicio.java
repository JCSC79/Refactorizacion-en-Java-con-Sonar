package refactorizada.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import refactorizada.dominio.FacturaConfiguracion;
import refactorizada.dominio.Producto;

/**
 * Servicio para cálculos financieros en el proceso de facturación.
 * <p>
 * Contiene la lógica de negocio para calcular subtotales, aplicar descuentos,
 * impuestos y costos de envío. Realiza operaciones con precisión decimal usando
 * {@link BigDecimal}.
 * </p>
 * 
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @since 2025
 * @see BigDecimal
 * @see RoundingMode
 */
public class FacturadorServicio {

    /**
     * Calcula el subtotal de la lista de productos sumando los subtotales individuales.
     * <p>
     * Recorre todos los productos de la lista y acumula sus valores mediante
     * {@link Producto#getSubtotal()}.
     * </p>
     * 
     * @param productos Lista de productos (no debe ser nula o vacía)
     * @return Subtotal calculado con precisión de 2 decimales
     * @throws IllegalArgumentException Si la lista de productos está vacía
     * @see Producto#getSubtotal()
     */
    public BigDecimal calcularSubtotal(List<Producto> productos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto p : productos) {
            total = total.add(p.getSubtotal());
        }
        return total;
    }

    /**
     * Calcula el total final aplicando descuentos, impuestos y envío.
     * <p>
     * El cálculo se realiza en este orden:
     * <ol>
     *   <li>Aplica el descuento al subtotal</li>
     *   <li>Calcula impuestos sobre el total con descuento</li>
     *   <li>Suma el costo de envío</li>
     * </ol>
     * Todos los valores se redondean a 2 decimales usando {@link RoundingMode#HALF_UP}.
     * </p>
     * 
     * @param subtotal Valor base para los cálculos (debe ser positivo)
     * @param config Configuración con porcentajes y valores aplicables
     * @return Total final con precisión de 2 decimales
     * @throws ArithmeticException Si ocurre error en operaciones aritméticas
     * @see FacturaConfiguracion
     * @see RoundingMode
     */
    public BigDecimal calcularTotalFinal(BigDecimal subtotal, FacturaConfiguracion config) {
        BigDecimal descuento = subtotal.multiply(config.getDescuento())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal totalConDescuento = subtotal.subtract(descuento);

        BigDecimal impuestos = totalConDescuento.multiply(config.getImpuesto())
                                             .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return totalConDescuento.add(impuestos).add(config.getEnvio());
    }
}
