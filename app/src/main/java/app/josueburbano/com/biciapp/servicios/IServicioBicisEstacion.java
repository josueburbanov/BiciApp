package app.josueburbano.com.biciapp.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.BiciEstacion;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServicioBicisEstacion {
    @GET("/BicisEstacionesServicio.svc/bicisEstaciones/biciEstacion")
    Call<BiciEstacion> obtenerBiciEstacion(@Query("hist")boolean historico,@Query("estacion") String idEstacion, @Query("bici")String idBici);

    @GET("/BicisEstacionesServicio.svc/bicisEstaciones/bicicletas/{idEstacion}")
    Call<List<Bicicleta>> obtenerBicisByEstacion(@Path("idEstacion") String idEstacion);
}
