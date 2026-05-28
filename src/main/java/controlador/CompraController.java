package controlador;

import modelo.Cliente;
import modelo.SistemaVentaEntradas;
import modelo.Tarjeta;
import modelo.Venta;

public class CompraController {

    private SistemaVentaEntradas sistema;

    public CompraController(SistemaVentaEntradas sistema) {
        this.sistema = sistema;
    }

    public String obtenerZonasDisponibles() {
        return sistema.listarZonasDisponibles();
    }
    public String obtenerDatosConcierto() {
        return sistema.obtenerDatosConcierto();
    }

    public boolean registrarCliente(Cliente cliente) {
        return sistema.registrarCliente(cliente);
    }

    public String comprarEntrada(Cliente cliente, String nombreZona, int cantidad, Tarjeta tarjeta) {
        try {
            Venta venta = sistema.procesarVenta(cliente, nombreZona, cantidad, tarjeta);
            return "Compra realizada correctamente:\n" + venta.mostrarDetalleVenta();
        } catch (IllegalArgumentException e) {
            return "Error en la compra: " + e.getMessage();
        }
    }
}