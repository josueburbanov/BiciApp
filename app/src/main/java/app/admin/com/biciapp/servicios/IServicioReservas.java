package app.admin.com.biciapp.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp.datos.modelos.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IServicioReservas {
    @POST("/ReservasServicio.svc/reservas/nueva")
    Call<Reserva> crearNuevaReserva(@Body JsonObject body);

    @GET("/ReservasServicio.svc/reservas/cliente/{idCliente}")
    Call<List<Reserva>> obtenerReservas(@Path("idCliente") String idCliente);

    @GET("/ReservasServicio.svc/reservas/cliente/{idCliente}/activa")
    Call<Reserva> obtenerReservaActiva(@Path("idCliente") String idCliente);

    @POST("/ReservasServicio.svc/reservas/cancelar/{idReserva}")
    Call<Reserva> cancelarReserva(@Path("idReserva") String idReserva);


}
