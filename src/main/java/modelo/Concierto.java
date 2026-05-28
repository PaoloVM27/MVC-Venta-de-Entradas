package modelo;

import java.util.ArrayList;

public class Concierto {

    private String nombre;
    private String fecha;
    private String lugar;
    private ArrayList<Zona> zonas;

    public Concierto(String nombre, String fecha, String lugar) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.zonas = new ArrayList<>();
    }

    public boolean agregarZona(Zona zona) {
        if (zonas.size() < 4) {
            zonas.add(zona);
            return true;
        }
        return false;
    }

    public boolean eliminarZona(String nombreZona) {
        for (int i = 0; i < zonas.size(); i++) {
            Zona zona = zonas.get(i);

            if (zona.getNombre().equalsIgnoreCase(nombreZona)) {
                zonas.remove(i);
                return true;
            }
        }

        return false;
    }

    public Zona buscarZona(String nombreZona) {
        for (int i = 0; i < zonas.size(); i++) {
            Zona zona = zonas.get(i);

            if (zona.getNombre().equalsIgnoreCase(nombreZona)) {
                return zona;
            }
        }

        return null;
    }

    public int obtenerCapacidadTotal() {
        int total = 0;

        for (int i = 0; i < zonas.size(); i++) {
            total = total + zonas.get(i).getCapacidad();
        }

        return total;
    }

    public int obtenerCapacidadDisponibleTotal() {
        int total = 0;

        for (int i = 0; i < zonas.size(); i++) {
            total = total + zonas.get(i).getCapacidadDisponible();
        }

        return total;
    }

    public String mostrarZonas() {
        String texto = "";

        for (int i = 0; i < zonas.size(); i++) {
            Zona zona = zonas.get(i);

            texto = texto + "Zona: " + zona.getNombre()
                    + " | Precio: S/ " + zona.getPrecio()
                    + " | Disponible: " + zona.getCapacidadDisponible()
                    + "\n";
        }

        return texto;
    }
    public String mostrarDatosConcierto() {
        return "Concierto: " + nombre
                + "\nFecha: " + fecha
                + "\nLugar: " + lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public ArrayList<Zona> getZonas() {
        return zonas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setZonas(ArrayList<Zona> zonas) {
        this.zonas = zonas;
    }
    
}