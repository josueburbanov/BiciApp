package app.josueburbano.com.biciapp.datos.modelos;

import java.io.Serializable;

public class Estacion implements Serializable {
    private double longitud;
    private double latitud;
    private String nombre;

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
