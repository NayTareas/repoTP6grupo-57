package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CreditoTest {

    private Credito credito;
    private Factura factura;
    private TarjetaCredito tarjeta;

    @BeforeEach
    void setUp() {
        credito = new Credito();
        factura = new Factura();
        tarjeta = new TarjetaCredito();
    }

    @Test
    void testMontoCreditoNoSupera1500000() {
        credito.setMonto(1500000);
        assertTrue(credito.getMontoTotal() <= 1500000, "El monto total del crédito no debe superar los 1.500.000$");
    }

    @Test
    void testSumaDetallesIgualTotalFactura() {
        List<Detalle> detalles = factura.getDetalles();
        double totalDetalles = detalles.stream()
                                       .mapToDouble(Detalle::getImporte)  // Acceder al importe directamente
                                       .sum();
        assertEquals(factura.calcularTotal(), totalDetalles, "La suma de los importes de los detalles debe ser igual al total de la factura");
    }


    @Test
    void testMontoTotalNoSuperaLimiteTarjeta() {
        double montoTotalCompra = 1500000; // Ejemplo
        tarjeta.setLimiteCompra(2000000); // Límite de la tarjeta
        assertTrue(montoTotalCompra <= tarjeta.getLimiteCompra(), "El monto total de la compra no debe superar el límite de la tarjeta");
    }
}
