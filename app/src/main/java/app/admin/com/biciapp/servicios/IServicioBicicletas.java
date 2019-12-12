package app.admin.com.biciapp.servicios;

import app.admin.com.biciapp.datos.modelos.Bicicleta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IServicioBicicletas {
    @GET("/BicicletasServicio.svc/bicicletas/{idBici}")
    Call<Bicicleta> obtenerBicicleta(@Path("idBici")String idBici);
}
