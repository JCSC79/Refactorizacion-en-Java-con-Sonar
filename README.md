# Facturas Java  
**Versión:** 1.0  
**Estado:** ✅ Activo  

## 🚀 Descripción  
Aplicación Java para generar facturas con:  
✔ **Cálculo automático** de subtotal, descuentos, impuestos y total final.  
✔ **Interfaz gráfica** intuitiva usando Java Swing.  
✔ **Arquitectura limpia** (MVC + Servicios).  

## 🔧 Tecnologías Usadas  
| **Tecnología**        | **Uso**                             |  
|-----------------------|-------------------------------------|  
| Java 17+              | Lógica principal                    |  
| Java Swing            | Interfaz gráfica                    |  
| BigDecimal            | Cálculos monetarios precisos        |  
| Git                   | Control de versiones                |  

## 📦 Estructura del Proyecto  

src/
├── refactorizada/
│ ├── dominio/ # Entidades (Producto, FacturaConfiguracion)
│ ├── servicios/ # Lógica de negocio (Facturador, Impresión)
│ ├── utilidades/ # Funciones auxiliares
│ └── gui/ # Interfaz gráfica (Frame + Controlador)
└── Main.java # Punto de entrada

## ⚙️ Requisitos  
- **JDK 17** o superior  
- **IDE**: IntelliJ, Eclipse o VSCode  

## 🛠️ Instalación
1. Clona el repositorio
2. Importa el proyecto en tu IDE
3. Ejecuta `Main.java`.  

## 🧪 Casos de Prueba  
| **Escenario**                | **Entrada**                          | **Salida Esperada**                  |  
|------------------------------|--------------------------------------|--------------------------------------|  
| Compra básica sin descuento  | 2 productos + 10% impuestos          | Total = Precio + Impuesto            |  
| Descuento + Envío            | Descuento 15% + Envío $5.50          | Total = Subtotal - Descuento + Envío |  

