package app.admin.com.biciapp.datos.modelos;

import java.io.Serializable;

public class Bicicleta implements Serializable {
    private boolean estado;
    private String modelo;
    private String id;

    public boolean getEstado() {
        return estado;
    }

    public String getModelo() {
        return modelo;
    }

    @Override
    public String toString() {
        return modelo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
