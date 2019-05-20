package app.josueburbano.com.biciapp.datos.modelos;

import java.io.Serializable;

public class Bicicleta implements Serializable {
    private String estado;
    private String modelo;

    public String getEstado() {
        return estado;
    }

    public String getModelo() {
        return modelo;
    }

    @Override
    public String toString() {
        return modelo;
    }
}
