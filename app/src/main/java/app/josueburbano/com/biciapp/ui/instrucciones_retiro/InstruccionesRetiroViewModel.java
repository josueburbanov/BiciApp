package app.josueburbano.com.biciapp.ui.instrucciones_retiro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp.datos.BicisCandadosRepository;
import app.josueburbano.com.biciapp.datos.CandadosRepository;
import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp.datos.modelos.Candado;

public class InstruccionesRetiroViewModel extends ViewModel {
    private LiveData<BiciCandado> biciEstacion;
    private BicisCandadosRepository biciEstacionRepo;
    private CandadosRepository candadosRepository;
    private LiveData<Candado> candado;


    public InstruccionesRetiroViewModel(BicisCandadosRepository biciEstacionRepo,
                                        CandadosRepository candadosRepository) {
        this.biciEstacionRepo = biciEstacionRepo;
        this.biciEstacion = biciEstacionRepo.getData();

        this.candadosRepository = candadosRepository;
        this.candado = candadosRepository.getCandado();
    }

    //Para llamar
    public void obtenerBiciEstacion(String idBici) {
        biciEstacion = biciEstacionRepo.getBiciEstacion(idBici, null);
    }
    public void obtenerCandado(String idCandado){
        candado = candadosRepository.obtenerCandado(idCandado);
    }

    //Para observar
    public LiveData<BiciCandado> getBiciEstacion() {
        return this.biciEstacion;
    }
    public LiveData<Candado> getCandado() {return this.candado;}

}
