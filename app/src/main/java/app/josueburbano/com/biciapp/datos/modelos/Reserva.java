package app.josueburbano.com.biciapp.datos.modelos;

public class Reserva {
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
}
