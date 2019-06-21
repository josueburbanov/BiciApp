package app.josueburbano.com.biciapp.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServicioBicisCandados {
    @GET("/BicisCandadosServicio.svc/bicisCandados/biciCandadoActual")
    Call<BiciCandado> obtenerBiciCandadoByIds(@Query("candado") String idCandado, @Query("bici")String idBici);

    @GET("/BicisCandadosServicio.svc/bicisCandados/bicicletas/{idEstacion}")
    Call<List<Bicicleta>> obtenerBicisByEstacion(@Path("idEstacion") String idEstacion);

    @POST("/BicisCandadosServicio.svc/bicisCandados/nueva")
    Call<BiciCandado> agregarBiciCandado(@Body JsonObject body);
}
