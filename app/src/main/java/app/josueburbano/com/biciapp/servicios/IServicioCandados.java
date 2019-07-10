package app.josueburbano.com.biciapp.servicios;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Candado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IServicioCandados {
    @GET("/CandadosServicio.svc/candados/abiertos/estacion/{idEstacion}")
    Call<List<Candado>> obtenerCandadosAbiertos(@Path("idEstacion")String idEstacion);

    @GET("/CandadosServicio.svc/candados/{idCandado}")
    Call<Candado> obtenerCandado(@Path("idCandado") String idCandado);
}
