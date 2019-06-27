package app.josueburbano.com.biciapp.datos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.Candado;
import app.josueburbano.com.biciapp.servicios.IServicioBicisCandados;
import app.josueburbano.com.biciapp.servicios.IServicioCandados;
import app.josueburbano.com.biciapp.servicios.IServicioCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CandadosRepository {
    private IServicioBicisCandados webservice;
    private MutableLiveData<List<Candado>> data = new MutableLiveData<>();

    public LiveData<List<Candado>> getCandadosAbiertos(String idEstacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        Call<List<Candado>> requestCandadosAbiertos = service.obtenerCandadosAbiertos(idEstacion);
        requestCandadosAbiertos.enqueue(new Callback<List<Candado>>() {
            @Override
            public void onResponse(Call<List<Candado>> call, Response<List<Candado>> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                }else{
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Candado>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<Candado>> getData() {
        return data;
    }

}
