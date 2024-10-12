package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.Credito;

public class CreditoTest {
    private Factura factura;
    private Cliente cliente;

    @Test
    public void testMontoTotalCreditoNoSuperaPermitido() {
        Credito credito = new Credito();
        assertTrue(credito.getMontoTotal() <= 1500000, "El monto total del crédito no debe superar 1.500.000$");
    }
    
    @BeforeEach
    public void setUp() {
   	 	 
        Producto producto1 = new Producto(1001L, "Lavadora", 800000);
        Producto producto2 = new Producto(1002L, "Celular", 500000);
        Detalle detalle1 = new Detalle(1, producto1.getPrecioUnitario(), producto1);
        Detalle detalle2 = new Detalle(1, producto2.getPrecioUnitario(), producto2);
        factura = new Factura(LocalDate.now(), 1234L, null, List.of(detalle1, detalle2));

        
        cliente = new Cliente(12345678L, "Juan Pérez", "jperez@gmail.com");
        TarjetaCredito tarjeta = new TarjetaCredito(2000000, cliente); 
        cliente.setTarjetaCredito(tarjeta);
    }

    // PRIMER TEST: Valida que la suma de los importes de los detalles sea igual al total de la factura
    @Test
    public void testSumaDetallesIgualTotalFactura() {
        double sumaDetalles = factura.getDetalles().stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();

        assertEquals(sumaDetalles, factura.getTotal(), "La suma de los importes de los detalles debe ser igual al total de la factura.");
    }

    // SEGUNDO TEST: Valida que el monto total no supere $1.500.000 y el límite disponible en la tarjeta del cliente
    @Test
    public void testMontoTotalCompraNoSuperaPermitido() {
   	 
        // Simula un crédito basado en la factura y la tarjeta de crédito del cliente
        Credito credito = new Credito(cliente.getTarjetaCredito(), factura, List.of());

        assertTrue(factura.getTotal() <= 1500000, "El monto total de la compra no debe superar 1.500.000$.");
        assertTrue(factura.getTotal() <= cliente.getTarjetaCredito().getLimiteCompra(), "El monto total de la compra no debe superar el límite disponible en la tarjeta del cliente.");
    }


}