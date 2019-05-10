package app.josueburbano.com.biciapp.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.Cliente;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IServicioEstaciones {

    @GET("/EstacionesServicio.svc/estaciones")
    Call<List<Estacion>> obtenerEstaciones();
}
