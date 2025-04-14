package refactorizada;

import java.math.BigDecimal;

/**
 * Clase que representa un producto con nombre, cantidad y precio unitario.
 * Se utiliza para calcular el subtotal en una factura.
 */
public class Producto {
    private final String nombre;
    private final int cantidad;
    private final BigDecimal precioUnitario;

    /**
     * Constructor que inicializa los atributos del producto.
     * 
     * @param nombre          Nombre del producto.
     * @param cantidad        Cantidad comprada.
     * @param precioUnitario  Precio por unidad.
     */
    public Producto(String nombre, int cantidad, BigDecimal precioUnitario) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Calcula el subtotal del producto (precio unitario * cantidad).
     * 
     * @return Subtotal como BigDecimal.
     */
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
