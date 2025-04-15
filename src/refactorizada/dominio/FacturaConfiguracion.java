package refactorizada.dominio;

import java.math.BigDecimal;

public class FacturaConfiguracion {
    private final BigDecimal descuento;
    private final BigDecimal impuesto;
    private final BigDecimal envio;

    public FacturaConfiguracion(BigDecimal descuento, BigDecimal impuesto, BigDecimal envio) {
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.envio = envio;
    }

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
