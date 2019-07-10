package app.josueburbano.com.biciapp.servicios;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp.datos.modelos.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IServicioCliente {

    public static final String BASE_URL = "http:/192.168.100.64:45455";
    @POST("/ClientesServicio.svc/clientes/authentication")
    Call<Cliente> obtenerClienteLogueado(@Query("usuario") String usuario, @Query("passw")String passw);

    @GET("/ClientesServicio.svc/clientes")
    Call<Cliente> obtenerClientes();

    @POST("/ClientesServicio.svc/clientes/nuevo")
    Call<Cliente> crearCliente(@Body JsonObject body);
}
