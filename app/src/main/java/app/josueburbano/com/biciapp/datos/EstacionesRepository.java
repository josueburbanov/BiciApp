package app.josueburbano.com.biciapp.datos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.servicios.IServicioBicisCandados;
import app.josueburbano.com.biciapp.servicios.IServicioCliente;
import app.josueburbano.com.biciapp.servicios.IServicioEstaciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstacionesRepository {
    private IServicioEstaciones webservice;
    private MutableLiveData<List<Estacion>> data = new MutableLiveData<>();
    private MutableLiveData<Estacion> estacion = new MutableLiveData<>();

    public LiveData<List<Estacion>> getEstaciones() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioEstaciones service = retrofit.create(IServicioEstaciones.class);

        Call<List<Estacion>> requestEstaciones = service.obtenerEstaciones();
        requestEstaciones.enqueue(new Callback<List<Estacion>>() {
            @Override
            public void onResponse(Call<List<Estacion>> call, Response<List<Estacion>> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                }else{
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Estacion>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Estacion> getEstacion(String idEstacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioEstaciones service = retrofit.create(IServicioEstaciones.class);

        Call<Estacion> requestEstaciones = service.obtenerEstacion(idEstacion);
        requestEstaciones.enqueue(new Callback<Estacion>() {
            @Override
            public void onResponse(Call<Estacion> call, Response<Estacion> response) {
                if (!response.isSuccessful()) {
                    estacion.setValue(null);
                }else{
                    estacion.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Estacion> call, Throwable t) {
                estacion.setValue(null);
            }
        });
        return estacion;
    }

    public LiveData<Estacion> getEstacionByBici(String idBici) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicisCandados service = retrofit.create(IServicioBicisCandados.class);

        Call<Estacion> requestEstaciones = service.obtenerEstacionByBici(idBici);
        requestEstaciones.enqueue(new Callback<Estacion>() {
            @Override
            public void onResponse(Call<Estacion> call, Response<Estacion> response) {
                if (!response.isSuccessful()) {
                    estacion.setValue(null);
                }else{
                    estacion.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Estacion> call, Throwable t) {
                estacion.setValue(null);
            }
        });
        return estacion;
    }



    public MutableLiveData<List<Estacion>> getData() {
        return data;
    }
    public MutableLiveData<Estacion> getEstacion() {
        return estacion;
    }
}
