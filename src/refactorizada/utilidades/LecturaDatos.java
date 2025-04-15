package refactorizada.utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import refactorizada.dominio.Producto;
import refactorizada.dominio.FacturaConfiguracion;

public class LecturaDatos {
    private static final Logger logger = Logger.getLogger(LecturaDatos.class.getName());

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
}
