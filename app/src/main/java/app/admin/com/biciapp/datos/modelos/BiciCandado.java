package app.admin.com.biciapp.datos.modelos;

public class BiciCandado {
    private String id;
    private String fechaHora;
    private boolean entregaRetiro;
    private String idBici;
    private String idCandado;
    private String error;
    private boolean statusEntregaRecepcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean getEntregaRetiro() {
        return entregaRetiro;
    }

    public void setEntregaRetiro(boolean entregaRetiro) {
        this.entregaRetiro = entregaRetiro;
    }

    public String getIdBici() {
        return idBici;
    }

    public void setIdBici(String idBici) {
        this.idBici = idBici;
    }

    public String getIdCandado() {
        return idCandado;
    }

    public void setIdCandado(String idCandado) {
        this.idCandado = idCandado;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean getStatusEntregaRecepcion() {
        return statusEntregaRecepcion;
    }

    public void setStatusEntregaRecepcion(boolean statusEntregaRecepcion) {
        this.statusEntregaRecepcion = statusEntregaRecepcion;
    }
}
