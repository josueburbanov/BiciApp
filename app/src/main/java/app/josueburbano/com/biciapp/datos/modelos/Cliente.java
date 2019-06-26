package app.josueburbano.com.biciapp.datos.modelos;

public class Cliente {
    private String id;
    private String usuario;
    private String cedula;
    private String nombre;
    private String correoElectronico;
    private String direccion;
    private String passw;
    private String telefono;
    private String rfid;

    public Cliente(String id, String usuario) {
        this.setId(id);
        this.setUsuario(usuario);
    }

    public String getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
}
