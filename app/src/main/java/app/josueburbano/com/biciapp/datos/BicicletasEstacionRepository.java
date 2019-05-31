package app.josueburbano.com.biciapp.datos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import app.josueburbano.com.biciapp.datos.modelos.BiciEstacion;
import app.josueburbano.com.biciapp.servicios.IServicioBicisEstacion;
import app.josueburbano.com.biciapp.servicios.IServicioCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BicicletasEstacionRepository {
    private IServicioBicisEstacion webservice;
    private MutableLiveData<BiciEstacion> data = new MutableLiveData<>();

    public LiveData<BiciEstacion> getBiciEstacion(boolean hist, String idBici, String idEstacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicisEstacion service = retrofit.create(IServicioBicisEstacion.class);

        Call<BiciEstacion> requestBicisEstacion = service.obtenerBiciEstacion(hist, idEstacion, idBici);
        requestBicisEstacion.enqueue(new Callback<BiciEstacion>() {
            @Override
            public void onResponse(Call<BiciEstacion> call, Response<BiciEstacion> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                }else{
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BiciEstacion> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<BiciEstacion> getData() {
        return data;
    }
}
