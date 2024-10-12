package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Credito {

    private TarjetaCredito tarjetaCredito;
    private Factura factura;
    private List<Cuota> cuotas = new ArrayList<Cuota>();

    public Credito() {
    }

    public Credito(TarjetaCredito tarjetaCredito, Factura factura, List<Cuota> cuotas) {
        this.tarjetaCredito = tarjetaCredito;
        this.factura = factura;
        this.cuotas = cuotas;
        generarCuotas(); // Llama a generar las cuotas
    }

    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    public void generarCuotas() {
        if (!cuotas.isEmpty()) {
            return;
        }

        if (factura != null) {
            double montoPorCuota = factura.calcularTotal() / 30;
            LocalDate fechaInicial = LocalDate.now();
            for (int i = 1; i <= 30; i++) {
                Cuota cuota = new Cuota();
                cuota.setMonto(montoPorCuota);
                cuota.setNroCuota(i);
                cuota.setFechaGeneracion(fechaInicial);
                cuota.setFechaVencimiento(fechaInicial.plusMonths(i));
                cuotas.add(cuota);
            }
        }
    }



    // Método para calcular el monto total del crédito
    public double getMontoTotal() {
        double total = 0;
        for (Cuota cuota : cuotas) {
            total += cuota.getMonto();
        }
        return total;
    }

    public void mostrarCredito() {
        System.out.println("Tarjeta De Credito: " + tarjetaCredito + "\nFactura: " + factura + "\nCant. Cuotas:\n");
        for (Cuota cuota : cuotas) {
            System.out.println(cuota);
        }
    }
    public void setMonto(double montoTotal) {
        cuotas.clear(); // Limpiamos las cuotas previas si existían
        double montoPorCuota = montoTotal / 30;
        LocalDate fechaInicial = LocalDate.now();
        for (int i = 1; i <= 30; i++) {
            Cuota cuota = new Cuota();
            cuota.setMonto(montoPorCuota);
            cuota.setNroCuota(i);
            cuota.setFechaGeneracion(fechaInicial);
            cuota.setFechaVencimiento(fechaInicial.plusMonths(i));
            cuotas.add(cuota);
        }
    }

}
