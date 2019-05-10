package app.josueburbano.com.biciapp.servicios;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp.datos.modelos.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IServicioCliente {
    public static final String BASE_URL = "http:/192.168.100.57:45455";
    @POST("/ClientesServicio.svc/clientes/authentication")
    Call<Cliente> obtenerClienteLogueado(@Body JsonObject body);
}
