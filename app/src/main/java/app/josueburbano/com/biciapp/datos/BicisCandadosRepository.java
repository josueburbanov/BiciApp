package app.josueburbano.com.biciapp.datos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp.servicios.IServicioBicisCandados;
import app.josueburbano.com.biciapp.servicios.IServicioCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BicisCandadosRepository {
    private IServicioBicisCandados webservice;
    private MutableLiveData<BiciCandado> data = new MutableLiveData<>();

    public LiveData<BiciCandado> getBiciEstacion(String idBici, String idCandado) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicisCandados service = retrofit.create(IServicioBicisCandados.class);

        Call<BiciCandado> requestBicisEstacion = service.obtenerBiciCandadoByIds_Actual(idCandado, idBici);
        requestBicisEstacion.enqueue(new Callback<BiciCandado>() {
            @Override
            public void onResponse(Call<BiciCandado> call, Response<BiciCandado> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BiciCandado> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<BiciCandado> crearTransaccion(BiciCandado biciCandado) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicisCandados service = retrofit.create(IServicioBicisCandados.class);

        JsonObject biciCandadoJson = new JsonObject();
        biciCandadoJson.addProperty("idBici", biciCandado.getIdBici());
        biciCandadoJson.addProperty("idCandado", biciCandado.getIdCandado());
        biciCandadoJson.addProperty("fechaHora", biciCandado.getFechaHora());
        biciCandadoJson.addProperty("entregaRetiro", biciCandado.getEntregaRetiro());
        biciCandadoJson.addProperty("error", biciCandado.getError());
        biciCandadoJson.addProperty("statusEntregaRecepcion", biciCandado.getStatusEntregaRecepcion());

        //Llamada HTTP
        Call<BiciCandado> requestClienteL = service.agregarBiciCandado(biciCandadoJson);
        requestClienteL.enqueue(new Callback<BiciCandado>() {
            @Override
            public void onResponse(Call<BiciCandado> call, Response<BiciCandado> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BiciCandado> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;

    }

        public MutableLiveData<BiciCandado> getData () {
            return data;
        }
    }
