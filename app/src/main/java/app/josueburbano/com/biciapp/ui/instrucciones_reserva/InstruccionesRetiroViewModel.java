package app.josueburbano.com.biciapp.ui.instrucciones_reserva;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp.datos.BicicletasEstacionRepository;
import app.josueburbano.com.biciapp.datos.modelos.BiciEstacion;

public class InstruccionesRetiroViewModel extends ViewModel {
    private LiveData<BiciEstacion> biciEstacion;
    private BicicletasEstacionRepository biciEstacionRepo;

    public InstruccionesRetiroViewModel(BicicletasEstacionRepository biciEstacionRepo) {
        this.biciEstacionRepo = biciEstacionRepo;
        this.biciEstacion = biciEstacionRepo.getData();
    }

    //Para llamar
    public void obtenerBiciEstacion(String idEstacion, String idBici) {
        biciEstacion = biciEstacionRepo.getBiciEstacion(false, idBici, idEstacion);
    }

    //Para observar
    public LiveData<BiciEstacion> getBiciEstacion() {
        return this.biciEstacion;
    }

}
