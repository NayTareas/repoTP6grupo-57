package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.Credito;

public class CreditoTest {

    @Test
    public void testMontoTotalCreditoNoSuperaPermitido() {
        Credito credito = new Credito();
        assertTrue(credito.getMontoTotal() <= 1500000, "El monto total del crédito no debe superar 1.500.000$");
    }
}