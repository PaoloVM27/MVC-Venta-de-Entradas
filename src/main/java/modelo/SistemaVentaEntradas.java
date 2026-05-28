package modelo;

import java.util.ArrayList;

public class SistemaVentaEntradas {

    private ArrayList<Cliente> clientes;
    private ArrayList<Administrador> administradores;
    private ArrayList<Venta> ventas;
    private Concierto concierto;

    public SistemaVentaEntradas() {
        clientes = new ArrayList<>();
        administradores = new ArrayList<>();
        ventas = new ArrayList<>();

        inicializarDatos();
    }

    private void inicializarDatos() {
        concierto = new Concierto(
                "Concierto Aniversario UNMSM",
                "2026-09-24",
                "Estadio UNMSM"
        );

        concierto.agregarZona(new Zona("VIP", 100, 150.0));
        concierto.agregarZona(new Zona("Preferencial", 200, 100.0));
        concierto.agregarZona(new Zona("General", 500, 50.0));
        concierto.agregarZona(new Zona("Popular", 800, 30.0));

        Administrador admin = new Administrador(
                "Admin",
                "Principal",
                "admin",
                "1234"
        );

        administradores.add(admin);
    }

    public boolean registrarCliente(Cliente cliente) {
        if (buscarClientePorDni(cliente.getDni()) == null) {
            clientes.add(cliente);
            return true;
        }

        return false;
    }

    public Cliente buscarClientePorDni(String dni) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);

            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }

        return null;
    }

    public Administrador buscarAdministradorPorDni(String dni) {
        for (int i = 0; i < administradores.size(); i++) {
            Administrador admin = administradores.get(i);

            if (admin.getDni().equals(dni)) {
                return admin;
            }
        }

        return null;
    }

    public Cliente iniciarSesionCliente(String dni, String contrasena) {
        Cliente cliente = buscarClientePorDni(dni);

        if (cliente != null && cliente.iniciarSesion(dni, contrasena)) {
            return cliente;
        }

        return null;
    }

    public Administrador iniciarSesionAdministrador(String dni, String contrasena) {
        Administrador admin = buscarAdministradorPorDni(dni);

        if (admin != null && admin.iniciarSesion(dni, contrasena)) {
            return admin;
        }

        return null;
    }

    public Zona buscarZona(String nombreZona) {
        return concierto.buscarZona(nombreZona);
    }

    public String listarZonasDisponibles() {
        return concierto.mostrarZonas();
    }
    
    public String obtenerDatosConcierto() {
        return concierto.mostrarDatosConcierto();
    }

    public Venta procesarVenta(Cliente cliente, String nombreZona, int cantidadEntradas, Tarjeta tarjeta) {
        Zona zona = buscarZona(nombreZona);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no valido.");
        }

        if (zona == null) {
            throw new IllegalArgumentException("La zona seleccionada no existe.");
        }

        if (cantidadEntradas < 1 || cantidadEntradas > 4) {
            throw new IllegalArgumentException("Solo se puede comprar entre 1 y 4 entradas.");
        }

        if (!zona.verificarCapacidad(cantidadEntradas)) {
            throw new IllegalArgumentException("No hay capacidad disponible en la zona seleccionada.");
        }

        if (tarjeta == null || !tarjeta.validarTarjeta()) {
            throw new IllegalArgumentException("La tarjeta bancaria no es valida.");
        }

        int idVenta = ventas.size() + 1;
        Venta venta = new Venta(idVenta, cliente, zona, cantidadEntradas);

        PagoTarjeta pago = new PagoTarjeta(tarjeta, venta.getMontoTotal());

        if (pago.procesarPago()) {
            venta.confirmarVenta();
            ventas.add(venta);
            return venta;
        } else {
            throw new IllegalArgumentException("El pago fue rechazado.");
        }
    }

    public boolean anularVenta(int idVenta) {
        Venta venta = buscarVentaPorId(idVenta);

        if (venta != null) {
            return venta.anularVenta();
        }

        return false;
    }

    public Venta buscarVentaPorId(int idVenta) {
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);

            if (venta.getIdVenta() == idVenta) {
                return venta;
            }
        }

        return null;
    }

    public String listarVentas() {
        String texto = "";

        if (ventas.isEmpty()) {
            return "No hay ventas registradas.";
        }

        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);

            texto = texto + venta.mostrarDetalleVenta();
            texto = texto + "\n-----------------------------\n";
        }

        return texto;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public Concierto getConcierto() {
        return concierto;
    }
}