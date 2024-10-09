package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Producto;

public class CreditoTest {

    @Test
    public void testMontoTotalCreditoNoSuperaPermitido() {
        // Crear un cliente de ejemplo
        Cliente cliente = new Cliente(12345678, "Juan Pérez", "Calle Falsa 123", "1234567890", null);

        // Crear un producto de ejemplo
        Producto producto = new Producto(1L, "Televisor", 500000.00, "Argentina");

        // Crear un detalle con el producto
        Detalle detalle = new Detalle(1, producto.getPrecioUnitario(), producto);
        List<Detalle> detalles = new ArrayList<>();
        detalles.add(detalle);

        // Crear una factura con el cliente y los detalles
        Factura factura = new Factura(LocalDate.now(), 1001L, cliente, detalles);

        // Crear el crédito usando la factura
        Credito credito = new Credito(null, factura, new ArrayList<>());

        // Asegurarse de que el monto total no supere 1.500.000
        assertTrue(credito.getMontoTotal() <= 1500000, "El monto total del crédito no debe superar 1.500.000$");
    }
}
