package refactorizada.dominio;

import java.math.BigDecimal;

/**
 * Representa la configuración financiera aplicable a una factura.
 * <p>
 * Contiene valores decimales para descuentos, impuestos y gastos de envío.
 * Estos parámetros son utilizados por {@link refactorizada.servicios.FacturadorServicio}
 * para calcular el total final de la factura.
 * </p>
 * 
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @since 2025
 * @see BigDecimal
 */
public class FacturaConfiguracion {
    
    /** 
     * Porcentaje de descuento aplicable al subtotal.
     * <p>
     * Valor decimal entre 0 (0%) y 1 (100%), representado como BigDecimal para precisión financiera.
     * </p>
     */
    private final BigDecimal descuento;
    
    /** 
     * Porcentaje de impuesto aplicable al subtotal.
     * <p>
     * Valor decimal positivo representado como BigDecimal (ej: 0.21 para 21% de IVA).
     * </p>
     */
    private final BigDecimal impuesto;
    
    /** 
     * Coste fijo de envío asociado a la factura.
     * <p>
     * Valor decimal no negativo en formato BigDecimal.
     * </p>
     */
    private final BigDecimal envio;

    /**
     * Crea una configuración de factura con los parámetros especificados.
     * 
     * @param descuento  Porcentaje de descuento (debe ser entre 0 y 1)
     * @param impuesto   Porcentaje de impuesto (valor positivo)
     * @param envio      Coste de envío (valor no negativo)
     * @throws IllegalArgumentException Si algún parámetro no cumple las validaciones
     * @see BigDecimal#compareTo(BigDecimal)
     */
    public FacturaConfiguracion(BigDecimal descuento, BigDecimal impuesto, BigDecimal envio) {
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.envio = envio;
    }

    /**
     * Devuelve el porcentaje de descuento configurado.
     * 
     * @return Valor decimal del descuento (ej: 0.15 para 15%)
     */
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Obtiene el porcentaje de impuesto aplicable.
     * 
     * @return Valor decimal del impuesto (ej: 0.21 para 21% de IVA)
     */
    public BigDecimal getImpuesto() {
        return impuesto;
    }

    /**
     * Devuelve el coste de envío configurado.
     * 
     * @return Valor decimal no negativo del envío
     */
    public BigDecimal getEnvio() {
        return envio;
    }
}