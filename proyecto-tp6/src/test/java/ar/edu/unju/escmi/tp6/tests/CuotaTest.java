package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.Credito;

public class CuotaTest {

    @Test
    public void testListaCuotasNoNull() {
        Credito credito = new Credito();
        assertNotNull(credito.getCuotas(), "La lista de cuotas no debe ser null");
    }

    @Test
    public void testListaCuotasSiempreTreinta() {
        Credito credito = new Credito();
        assertEquals(30, credito.getCuotas().size(), "La lista de cuotas debe tener 30 elementos");
    }


    @Test
    public void testCantidadCuotasNoSuperaPermitido() {
        Credito credito = new Credito();
        assertTrue(credito.getCuotas().size() <= 30, "La cantidad de cuotas no debe superar 30");
    }
}