package refactorizada;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class FacturaUtils {
    private static final Logger logger = Logger.getLogger(FacturaUtils.class.getName());

    public static List<Producto> leerProductos(Scanner scanner) {
        List<Producto> productos = new ArrayList<>();
        try {
            System.out.print("Ingrese la cantidad de productos: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < cantidad; i++) {
                System.out.print("Nombre del producto: ");
                String nombre = scanner.nextLine();

                System.out.print("Cantidad: ");
                int unidades = Integer.parseInt(scanner.nextLine());

                System.out.print("Precio unitario: ");
                BigDecimal precio = new BigDecimal(scanner.nextLine());

                productos.add(new Producto(nombre, unidades, precio));
            }
        } catch (Exception e) {
            logger.severe("Error al leer los productos: " + e.getMessage());
        }
        return productos;
    }

    public static FacturaConfiguracion leerConfiguracionFactura(Scanner scanner) {
        try {
            System.out.print("Porcentaje de descuento (%): ");
            BigDecimal descuento = new BigDecimal(scanner.nextLine());

            System.out.print("Porcentaje de impuestos (%): ");
            BigDecimal impuesto = new BigDecimal(scanner.nextLine());

            System.out.print("Costo de envío: ");
            BigDecimal envio = new BigDecimal(scanner.nextLine());

            return new FacturaConfiguracion(descuento, impuesto, envio);
        } catch (Exception e) {
            logger.severe("Error al leer configuración de la factura: " + e.getMessage());
            return new FacturaConfiguracion(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }
    }

    public static BigDecimal calcularSubtotal(List<Producto> productos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto p : productos) {
            total = total.add(p.getSubtotal());
        }
        return total;
    }

    public static BigDecimal calcularTotalFinal(BigDecimal subtotal, FacturaConfiguracion config) {
        BigDecimal descuento = subtotal.multiply(config.getDescuento())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal totalConDescuento = subtotal.subtract(descuento);

        BigDecimal impuestos = totalConDescuento.multiply(config.getImpuesto())
                                             .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return totalConDescuento.add(impuestos).add(config.getEnvio());
    }

    public static void imprimirFactura(List<Producto> productos, BigDecimal subtotal,
                                      FacturaConfiguracion config, BigDecimal totalFinal) {
        System.out.println("\n--- FACTURA ---");
        for (Producto p : productos) {
            System.out.printf("%s x%d = %.2f\n", p.getNombre(), p.getCantidad(), p.getSubtotal());
        }

        BigDecimal descuento = subtotal.multiply(config.getDescuento())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal totalConDescuento = subtotal.subtract(descuento);
        BigDecimal impuestos = totalConDescuento.multiply(config.getImpuesto())
                                             .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        System.out.printf("Subtotal: %.2f\n", subtotal);
        System.out.printf("Descuento (%.2f%%): -%.2f\n", config.getDescuento(), descuento);
        System.out.printf("Impuestos (%.2f%%): +%.2f\n", config.getImpuesto(), impuestos);
        System.out.printf("Envío: +%.2f\n", config.getEnvio());
        System.out.printf("TOTAL FINAL: %.2f\n", totalFinal);
    }
}