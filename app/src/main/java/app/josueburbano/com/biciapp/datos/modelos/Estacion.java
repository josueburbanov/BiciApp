package app.josueburbano.com.biciapp.datos.modelos;

import java.io.Serializable;

public class Estacion implements Serializable {
    private double longitud;
    private double latitud;
    private String nombre;
    private String id;

    public double getLongitud() {
        return longitud;
    }


    public double getLatitud() {
        return latitud;
    }


    public String getNombre() {
        return nombre;
    }

    public String getId(){return id;}

}
