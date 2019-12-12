package app.admin.com.biciapp.servicios;

import java.util.List;

import app.admin.com.biciapp.datos.modelos.Estacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IServicioEstaciones {

    @GET("/EstacionesServicio.svc/estaciones")
    Call<List<Estacion>> obtenerEstaciones();

    @GET("/EstacionesServicio.svc/estaciones/{idEstacion}")
    Call<Estacion> obtenerEstacion(@Path("idEstacion")String idEstacion);
}
