package app.josueburbano.com.biciapp.ui.procesando_entrega;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp.datos.BicisCandadosRepository;
import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;

public class ProcesandoEntregaViewModel extends ViewModel {
    private LiveData<BiciCandado> biciCandado;
    private LiveData<BiciCandado> transaccion;
    private BicisCandadosRepository bicisCandadosRepository;

    public ProcesandoEntregaViewModel(BicisCandadosRepository bicisCandadosRepository) {
        this.bicisCandadosRepository = bicisCandadosRepository;
        this.biciCandado = this.bicisCandadosRepository.getData();
        this.transaccion = this.bicisCandadosRepository.getData();
    }

    //Para llamar
    public void realizarTransaccionParcial(BiciCandado transaccion_parcial) {
        this.transaccion = bicisCandadosRepository.crearTransaccion(transaccion_parcial);
    }

    public void checkLastTransaccion(String idBici){
        this.biciCandado = bicisCandadosRepository.getBiciEstacion(idBici,null);
    }

    //Para observar
    public LiveData<BiciCandado> getTransaccion() {
        return this.transaccion;
    }
    public LiveData<BiciCandado> getLastTransaccion() {
        return this.biciCandado;
    }
}
