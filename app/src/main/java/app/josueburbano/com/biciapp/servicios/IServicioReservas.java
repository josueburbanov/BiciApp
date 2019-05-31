package app.josueburbano.com.biciapp.servicios;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp.datos.modelos.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IServicioReservas {
    @POST("/ReservasServicio.svc/reservas/nueva")
    Call<Reserva> crearNuevaReserva(@Body JsonObject body);
}
