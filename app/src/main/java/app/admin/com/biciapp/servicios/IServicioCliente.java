package app.admin.com.biciapp.servicios;

import com.google.gson.JsonObject;

import app.admin.com.biciapp.datos.modelos.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IServicioCliente {

    //public static final String BASE_URL = "http:/192.168.100.105:45455";
    public static final String BASE_URL = "http:/192.168.100.118:45459";
    //public static final String BASE_URL = "http://ec2-34-217-59-122.us-west-2.compute.amazonaws.com/";
    @POST("/ClientesServicio.svc/clientes/authentication")
    Call<Cliente> obtenerClienteLogueado(@Query("usuario") String usuario, @Query("passw")String passw);

    @GET("/ClientesServicio.svc/clientes")
    Call<Cliente> obtenerClientes();

    @POST("/ClientesServicio.svc/clientes/nuevo")
    Call<Cliente> crearCliente(@Body JsonObject body);
}
