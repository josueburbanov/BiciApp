package app.josueburbano.com.biciapp.datos.repos;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import app.josueburbano.com.biciapp.datos.modelos.Cliente;

class ClienteCache {

    private String usuario;
    private MutableLiveData<Cliente> data;
    public LiveData<Cliente> get(String usuario) {
        if(usuario != null && data != null){
            return data;
        }
        return null;
    }

    public void put(String usuario, MutableLiveData<Cliente> data) {
        this.usuario = usuario;
        this.data = data;
    }
}
