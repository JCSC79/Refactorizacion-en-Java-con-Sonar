package refactorizada;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Scanner único para toda la aplicación
        
        // Paso 1: leer productos
        List<Producto> productos = FacturaUtils.leerProductos(scanner);

        // Paso 2: leer configuración
        FacturaConfiguracion config = FacturaUtils.leerConfiguracionFactura(scanner);

        // Paso 3: calcular subtotal
        BigDecimal subtotal = FacturaUtils.calcularSubtotal(productos);

        // Paso 4: calcular total final
        BigDecimal totalFinal = FacturaUtils.calcularTotalFinal(subtotal, config);

        // Paso 5: imprimir factura completa
        FacturaUtils.imprimirFactura(productos, subtotal, config, totalFinal);
        
        scanner.close();  // Cerrar scanner al final
    }
}