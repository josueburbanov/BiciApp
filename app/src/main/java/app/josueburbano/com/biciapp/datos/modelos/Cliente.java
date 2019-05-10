package app.josueburbano.com.biciapp.datos.modelos;

public class Cliente {
    private String id;
    private String usuario;

    public Cliente(String id, String usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

}
