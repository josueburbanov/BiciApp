package app.josueburbano.com.biciapp.datos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.servicios.IServicioBicisCandados;
import app.josueburbano.com.biciapp.servicios.IServicioCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BicicletasRepository {
    private IServicioBicisCandados webservice;
    private MutableLiveData<List<Bicicleta>> data = new MutableLiveData<>();

    public LiveData<List<Bicicleta>> getBicicletas(String idEstacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicisCandados service = retrofit.create(IServicioBicisCandados.class);

        Call<List<Bicicleta>> requestBicisEstacion = service.obtenerBicisByEstacion(idEstacion);
        requestBicisEstacion.enqueue(new Callback<List<Bicicleta>>() {
            @Override
            public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                }else{
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public MutableLiveData<List<Bicicleta>> getData() {
        return data;
    }
}
