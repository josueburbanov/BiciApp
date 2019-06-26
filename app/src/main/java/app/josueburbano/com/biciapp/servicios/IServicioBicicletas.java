package app.josueburbano.com.biciapp.servicios;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IServicioBicicletas {
    @GET("/BicicletasServicio.svc/bicicletas/{idBici}")
    Call<Bicicleta> obtenerBicicleta(@Path("idBici")String idBici);
}
