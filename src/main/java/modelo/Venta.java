package modelo;

import java.util.ArrayList;
import java.time.LocalDate;

public class Venta {

    private int idVenta;
    private Cliente cliente;
    private Zona zona;
    private ArrayList<Entrada> entradas;
    private String fechaVenta;
    private double montoTotal;
    private String estado;

    public Venta(int idVenta, Cliente cliente, Zona zona, int cantidadEntradas) {

        if (!validarCantidad(cantidadEntradas)) {
            throw new IllegalArgumentException("La cantidad de entradas debe ser entre 1 y 4.");
        }

        if (!zona.verificarCapacidad(cantidadEntradas)) {
            throw new IllegalArgumentException("No hay capacidad disponible en la zona seleccionada.");
        }

        this.idVenta = idVenta;
        this.cliente = cliente;
        this.zona = zona;
        this.entradas = new ArrayList<>();
        this.fechaVenta = LocalDate.now().toString();
        this.estado = "Pendiente";

        for (int i = 0; i < cantidadEntradas; i++) {
            Entrada entrada = new Entrada(i + 1, zona);
            entradas.add(entrada);
        }

        this.montoTotal = calcularTotal();
    }

    public boolean validarCantidad(int cantidadEntradas) {
        return cantidadEntradas >= 1 && cantidadEntradas <= 4;
    }

    public double calcularTotal() {
        return entradas.size() * zona.getPrecio();
    }

    public void confirmarVenta() {
        this.estado = "Confirmada";
        zona.disminuirCapacidad(entradas.size());
    }

    public boolean anularVenta() {
        if (estado.equals("Confirmada")) {
            estado = "Anulada";

            for (int i = 0; i < entradas.size(); i++) {
                Entrada entrada = entradas.get(i);
                entrada.anular();
            }

            zona.aumentarCapacidad(entradas.size());
            return true;
        }

        return false;
    }

    public String mostrarDetalleVenta() {
        String texto = "Venta Nro. " + idVenta
                + "\nCliente: " + cliente.getNombres() + " " + cliente.getApellidos()
                + "\nZona: " + zona.getNombre()
                + "\nCantidad de entradas: " + entradas.size()
                + "\nMonto total: S/ " + montoTotal
                + "\nEstado: " + estado
                + "\nFecha: " + fechaVenta
                + "\nEntradas:\n";

        for (int i = 0; i < entradas.size(); i++) {
            Entrada entrada = entradas.get(i);
            texto = texto + entrada.mostrarDatos() + "\n";
        }

        return texto;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Zona getZona() {
        return zona;
    }

    public ArrayList<Entrada> getEntradas() {
        return entradas;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
        this.montoTotal = calcularTotal();
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}