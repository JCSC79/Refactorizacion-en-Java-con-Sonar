// FacturaOriginal.java
import java.util.Scanner;

public class FacturaOriginal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de productos: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Limpia buffer

        double total = 0.0;
        for (int i = 0; i < n; i++) {
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese la cantidad: ");
            int cantidad = scanner.nextInt();

            System.out.print("Ingrese el precio unitario: ");
            double precio = scanner.nextDouble();
            scanner.nextLine(); // Limpia buffer

            double subtotal = cantidad * precio;
            total += subtotal;
        }

        System.out.print("Ingrese el porcentaje de descuento: ");
        double descuento = scanner.nextDouble();

        System.out.print("Ingrese el porcentaje de impuestos: ");
        double impuesto = scanner.nextDouble();

        System.out.print("Ingrese el costo de envío: ");
        double envio = scanner.nextDouble();

        double totalDescuento = total - (total * descuento / 100);
        double totalImpuestos = totalDescuento * impuesto / 100;
        double totalFinal = totalDescuento + totalImpuestos + envio;

        System.out.printf("Total final de la factura: %.2f%n", totalFinal);
        scanner.close();
    }
}
