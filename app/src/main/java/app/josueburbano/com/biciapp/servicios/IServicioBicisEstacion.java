package app.josueburbano.com.biciapp.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IServicioBicisEstacion {
    @GET("/BicisEstacionesServicio.svc/bicisEstaciones/{idEstacion}")
    Call<List<Bicicleta>> obtenerBicisByEstacion(@Path("idEstacion") String idEstacion);
}
