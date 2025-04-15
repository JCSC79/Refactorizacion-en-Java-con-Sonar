package refactorizada.dominio;

import java.math.BigDecimal;

public class Producto {
    private final String nombre;
    private final int cantidad;
    private final BigDecimal precioUnitario;

    public Producto(String nombre, int cantidad, BigDecimal precioUnitario) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
