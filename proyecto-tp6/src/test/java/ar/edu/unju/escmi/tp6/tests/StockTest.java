package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

public class StockTest {

    private Stock stock;
    private Producto producto;

    @BeforeEach
    public void setUp() {
        producto = new Producto(1001L, "Microondas", 50000);
        stock = new Stock(producto, 20);
        CollectionStock.agregarStock(stock);
    }

    @Test
    public void testDecrementaStock() {
        int cantidadComprada = 5;
        CollectionStock.reducirStock(stock, cantidadComprada);

        assertEquals(15, stock.getCantidad(), "El stock del producto debe decrementarse en la cantidad indicada.");
    }
}