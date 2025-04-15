package refactorizada.utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import refactorizada.dominio.FacturaConfiguracion;
import refactorizada.dominio.Producto;

/**
 * Clase utilitaria para la lectura de datos desde la consola.
 * <p>
 * Permite la entrada de datos de productos y configuración de facturas
 * a través de la consola, utilizando la clase {@link Scanner}.
 * </p>
 *
 * @author Juan Carlos Sandomingo
 * @version 1.0
 * @since 2025
 * @see Scanner
 * @see Logger
 */
public class LecturaDatos {

  /** Logger para registrar errores y eventos importantes. */
  private static final Logger logger = Logger.getLogger(LecturaDatos.class.getName());

  /**
   * Lee una lista de productos desde la consola.
   *
   * <p>Solicita al usuario la cantidad de productos y luego los datos de cada uno: nombre,
   * cantidad y precio unitario.
   *
   * @param scanner Scanner para leer la entrada del usuario
   * @return Lista de productos leídos desde la consola
   * @see Producto
   */
  @SuppressWarnings({"UseSpecificCatch", "LoggerStringConcat"})
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

  /**
   * Lee la configuración de la factura desde la consola.
   *
   * <p>Solicita al usuario el porcentaje de descuento, el porcentaje de impuestos y el costo de
   * envío.
   *
   * @param scanner Scanner para leer la entrada del usuario
   * @return Configuración de la factura leída desde la consola
   * @see FacturaConfiguracion
   */
  @SuppressWarnings("LoggerStringConcat")
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
