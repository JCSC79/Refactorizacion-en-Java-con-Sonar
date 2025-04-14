package refactorizada;

import java.math.BigDecimal;

/**
 * Clase que representa la configuración de una factura,
 * incluyendo descuento, impuestos y costo de envío.
 */
public class FacturaConfiguracion {
    private final BigDecimal descuento;
    private final BigDecimal impuesto;
    private final BigDecimal envio;

    /**
     * Constructor que inicializa los valores de configuración.
     * 
     * @param descuento Porcentaje de descuento aplicado.
     * @param impuesto  Porcentaje de impuestos aplicado.
     * @param envio     Costo adicional por envío.
     */
    public FacturaConfiguracion(BigDecimal descuento, BigDecimal impuesto, BigDecimal envio) {
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.envio = envio;
    }

    // Getters para acceder a los valores configurados

    public BigDecimal getDescuento() {
        return descuento;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public BigDecimal getEnvio() {
        return envio;
    }
}
