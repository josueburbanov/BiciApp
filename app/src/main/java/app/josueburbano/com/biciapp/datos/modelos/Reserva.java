package app.josueburbano.com.biciapp.datos.modelos;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {
    private String id;
    private String idBici;
    private String idCliente;
    private String horaInicio;
    private String horaFin;
    private String fecha;

    public String getIdBici() {
        return idBici;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdBici(String idBici) {
        this.idBici = idBici;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
