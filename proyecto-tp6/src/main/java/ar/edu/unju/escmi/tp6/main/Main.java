package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;


public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Precarga de datos
        CollectionTarjetaCredito.precargarTarjetas();
        CollectionCliente.precargarClientes();
        CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();

        int opcion = 0;

        do {
            System.out.println("\n====== Menu Principal =====");
            System.out.println("1- Realizar una venta con el programa 'Ahora 30'");
            System.out.println("2- Ver compras realizadas por el cliente (ingresando el DNI del cliente)");
            System.out.println("3- Lista de los electrodomésticos disponibles en el programa 'Ahora 30'");
            System.out.println("4- Consultar stock de los electrodomésticos del programa 'Ahora 30'");
            System.out.println("5- Revisar créditos de un cliente (ingresando el DNI del cliente)");
            System.out.println("6- Salir");

            System.out.print("Ingrese su opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    realizarVentaAhora30();
                    break;
                case 2:
                    revisarCompras();
                    break;
                case 3:
                    mostrarElectrodomesticosAhora30();
                    break;
                case 4:
                    consultarStockAhora30();
                    break;
                case 5:
                    revisarCreditos();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 6);

        scanner.close();
    }

    // 1. Realizar una venta con el programa "Ahora 30"
    public static void realizarVentaAhora30() {
        System.out.print("Ingrese el DNI del cliente: ");
        long dni = scanner.nextLong();

        Cliente cliente = CollectionCliente.buscarCliente(dni);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        TarjetaCredito tarjeta = cliente.getTarjetaCredito();
        if (tarjeta == null) {
            System.out.println("El cliente no tiene tarjeta de crédito.");
            return;
        }

        System.out.println("Seleccione el producto que desea comprar: ");
        CollectionProducto.mostrarProductosAhora30();
        long codigoProducto = scanner.nextLong();

        Producto producto = CollectionProducto.buscarProducto(codigoProducto);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        if (producto.getDescripcion().toLowerCase().contains("celular") && producto.getPrecioUnitario() > 800000) {
            System.out.println("Los teléfonos no pueden superar los $800.000 en el programa 'Ahora 30'.");
            return;
        } else if (producto.getPrecioUnitario() > 1500000) {
            System.out.println("El precio no puede superar los $1.500.000 en el programa 'Ahora 30'.");
            return;
        }

        if (tarjeta.getLimiteCompra() < producto.getPrecioUnitario()) {
            System.out.println("Límite insuficiente en la tarjeta de crédito del cliente.");
            return;
        }

        // Crear el detalle de la compra
        Detalle detalle = new Detalle(1, producto.getPrecioUnitario(), producto);

        // Crear la factura
        Factura factura = new Factura(LocalDate.now(), (long) (Math.random() * 10000), cliente, List.of(detalle));

        // Agregar la factura a la colección
        CollectionFactura.agregarFactura(factura);

        // Crear el crédito y agregar las cuotas
        Credito credito = new Credito(tarjeta, factura, new ArrayList<>());
        credito.generarCuotas();

        // Reducir el límite de la tarjeta y el stock del producto
        tarjeta.reducirLimite(producto.getPrecioUnitario());
        CollectionStock.reducirStock(CollectionStock.buscarStock(producto), 1);

        // Guardar el crédito en la colección de créditos
        CollectionCredito.agregarCredito(credito);

        System.out.println("Venta realizada exitosamente bajo el programa 'Ahora 30'. Monto total: $" + producto.getPrecioUnitario());
    }


    // 2. Ver compras realizadas por el cliente
    public static void revisarCompras() {
        System.out.print("Ingrese el DNI del cliente: ");
        long dni = scanner.nextLong();

        Cliente cliente = CollectionCliente.buscarCliente(dni);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Compras realizadas por " + cliente.getNombre() + ":");
        List<Factura> facturas = cliente.consultarCompras();
        if (facturas.isEmpty()) {
            System.out.println("No se encontraron compras para este cliente.");
        } else {
            for (Factura factura : facturas) {
                System.out.println(factura);
            }
        }
    }

    public static void mostrarElectrodomesticosAhora30() {
        System.out.println("Electrodomésticos disponibles en el programa 'Ahora 30':");
        CollectionProducto.mostrarProductosAhora30(); // Invocando el método que lista productos válidos
    }

    // 4. Consultar stock de electrodomésticos incluidos en "Ahora 30"
    public static void consultarStockAhora30() {
        System.out.println("Stock disponible de los productos en el programa 'Ahora 30':");
        CollectionStock.mostrarStockAhora30(); // Mostrar solo el stock de productos válidos para el programa
    }

 // 5. Revisar los créditos de un cliente
    public static void revisarCreditos() {
        System.out.print("Ingrese el DNI del cliente: ");
        long dni = scanner.nextLong();

        Cliente cliente = CollectionCliente.buscarCliente(dni);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Créditos de " + cliente.getNombre() + ":");
        boolean tieneCreditos = false; // Bandera para verificar si tiene créditos

        for (Credito credito : CollectionCredito.creditos) {
            if (credito.getTarjetaCredito() != null && credito.getTarjetaCredito().getCliente() != null &&
                credito.getTarjetaCredito().getCliente().getDni() == dni) {
                credito.mostarCredito(); // Muestra los detalles del crédito
                tieneCreditos = true; // Marca que el cliente tiene al menos un crédito
            }
        }

        if (!tieneCreditos) {
            System.out.println("No se encontraron créditos para este cliente.");
        }
    }
}
