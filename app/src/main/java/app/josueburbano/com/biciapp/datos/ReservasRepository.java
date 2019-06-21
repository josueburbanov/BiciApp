package app.josueburbano.com.biciapp.datos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp.datos.modelos.Reserva;
import app.josueburbano.com.biciapp.servicios.IServicioCliente;
import app.josueburbano.com.biciapp.servicios.IServicioReservas;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservasRepository {
    private MutableLiveData<Reserva> data = new MutableLiveData<>();

    public LiveData<Reserva> crearReserva(Reserva reserva) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        JsonObject reservaJson = new JsonObject();
        reservaJson.addProperty("idBici", reserva.getIdBici());
        reservaJson.addProperty("idCliente", reserva.getIdCliente());
        reservaJson.addProperty("fecha", reserva.getFecha());
        reservaJson.addProperty("horaInicio", reserva.getHoraInicio());
        reservaJson.addProperty("horaFin", reserva.getHoraFin());

        //Llamada HTTP
        Call<Reserva> requestClienteL = service.crearNuevaReserva(reservaJson);
        requestClienteL.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public MutableLiveData<Reserva> getData() {
        return data;
    }
}
