package app.josueburbano.com.biciapp.datos;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp.datos.modelos.Cliente;
import app.josueburbano.com.biciapp.servicios.IServicioCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRepository {
    private IServicioCliente webservice;

    // Simple in-memory cache. Details omitted for brevity.
    private ClienteCache clienteCache = new ClienteCache();

    private MutableLiveData<Cliente> data = new MutableLiveData<>();

    public LiveData<Cliente> getCliente(String usuario, String passw) {
        //LiveData<Cliente> cached = clienteCache.get(usuario);
        /*if (cached != null) {
            return cached;
        }*/

        //clienteCache.put(usuario, data);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioCliente.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCliente service = retrofit.create(IServicioCliente.class);


        JsonObject credenciales = new JsonObject();
        credenciales.addProperty("usuario", usuario);
        credenciales.addProperty("passw", passw);

        //Llamada HTTP
        Call<Cliente> requestClienteL = service.obtenerClienteLogueado(credenciales);
        requestClienteL.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;

    }


    public MutableLiveData<Cliente> getData() {
        return data;
    }
}
