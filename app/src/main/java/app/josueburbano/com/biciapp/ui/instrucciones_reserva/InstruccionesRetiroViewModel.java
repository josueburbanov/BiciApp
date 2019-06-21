package app.josueburbano.com.biciapp.ui.instrucciones_reserva;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp.datos.BicisCandadosRepository;
import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;

public class InstruccionesRetiroViewModel extends ViewModel {
    private LiveData<BiciCandado> biciEstacion;
    private BicisCandadosRepository biciEstacionRepo;

    public InstruccionesRetiroViewModel(BicisCandadosRepository biciEstacionRepo) {
        this.biciEstacionRepo = biciEstacionRepo;
        this.biciEstacion = biciEstacionRepo.getData();
    }

    //Para llamar
    public void obtenerBiciEstacion(String idBici) {
        biciEstacion = biciEstacionRepo.getBiciEstacion(idBici, null);
    }

    //Para observar
    public LiveData<BiciCandado> getBiciEstacion() {
        return this.biciEstacion;
    }

}
