package refactorizada.dominio;

import java.math.BigDecimal;

/**
 * Representa un producto con sus atributos básicos para facturación.
 * <p>
 * Esta clase encapsula la información esencial de un producto: nombre, cantidad y precio unitario.
 * Es utilizada por {@link refactorizada.servicios.FacturadorServicio} para realizar cálculos financieros.
 * </p>
 * 
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @since 2025
 * @see BigDecimal
 */
public class Producto {
    
    /** 
     * Nombre descriptivo del producto (ej: "Teclado mecánico RGB").
     * <p>
     * No puede ser nulo o vacío.
     * </p>
     */
    private final String nombre;
    
    /** 
     * Cantidad de unidades del producto (valor entero positivo).
     * <p>
     * Debe ser mayor que cero.
     * </p>
     */
    private final int cantidad;
    
    /** 
     * Precio unitario del producto en formato BigDecimal para precisión monetaria.
     * <p>
     * Valor decimal positivo con soporte para operaciones financieras.
     * </p>
     */
    private final BigDecimal precioUnitario;

    /**
     * Crea una instancia de Producto con los parámetros especificados.
     * 
     * @param nombre          Nombre descriptivo del producto (no puede ser vacío)
     * @param cantidad        Número entero positivo de unidades
     * @param precioUnitario  Valor decimal positivo del precio unitario
     * @throws IllegalArgumentException Si alguno de los parámetros no cumple las validaciones
     * @see BigDecimal#valueOf(long)
     */
    public Producto(String nombre, int cantidad, BigDecimal precioUnitario) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    /**
     * Devuelve el nombre del producto.
     * 
     * @return Nombre actual del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la cantidad de unidades del producto.
     * 
     * @return Cantidad actual como entero positivo
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Devuelve el precio unitario del producto.
     * 
     * @return Precio actual en formato BigDecimal
     * @see BigDecimal
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Calcula el subtotal del producto (precio unitario * cantidad).
     * 
     * @return Valor total del producto sin impuestos
     * @see BigDecimal#multiply(BigDecimal)
     */
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
