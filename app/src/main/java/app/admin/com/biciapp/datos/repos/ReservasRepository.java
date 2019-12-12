package app.admin.com.biciapp.datos.repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp.servicios.IServicioCliente;
import app.admin.com.biciapp.datos.modelos.Reserva;
import app.admin.com.biciapp.servicios.IServicioReservas;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservasRepository {
    private MutableLiveData<Reserva> data = new MutableLiveData<>();
    private MutableLiveData<List<Reserva>> reservas = new MutableLiveData<>();

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
        reservaJson.addProperty("concreata", reserva.getConcretada());
        reservaJson.addProperty("activa", reserva.isActiva());

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

    public LiveData<List<Reserva>> obtenerReservas(String idCliente) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        //Llamada HTTP
        Call<List<Reserva>> requestClienteL = service.obtenerReservas(idCliente);
        requestClienteL.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if (!response.isSuccessful()) {
                    reservas.setValue(null);
                } else {
                    reservas.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                reservas.setValue(null);
            }
        });
        return reservas;
    }

    public LiveData<Reserva> obtenerReservaActiva(String idCliente) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        //Llamada HTTP
        Call<Reserva> requestClienteL = service.obtenerReservaActiva(idCliente);
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

    public LiveData<Reserva> cancelarReserva(String idReserva) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        //Llamada HTTP
        Call<Reserva> requestClienteL = service.cancelarReserva(idReserva);
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
    public MutableLiveData<List<Reserva>> getReservas() {return reservas;}
}
