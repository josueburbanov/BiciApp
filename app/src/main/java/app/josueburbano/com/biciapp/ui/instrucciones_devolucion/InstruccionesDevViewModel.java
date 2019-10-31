package app.josueburbano.com.biciapp.ui.instrucciones_devolucion;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.josueburbano.com.biciapp.datos.repos.CandadosRepository;
import app.josueburbano.com.biciapp.datos.repos.EstacionesRepository;
import app.josueburbano.com.biciapp.datos.modelos.Candado;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;

public class InstruccionesDevViewModel extends ViewModel {

    private LiveData<List<Estacion>> estaciones;
    private EstacionesRepository estacionesRepo;

    private LiveData<List<Candado>> candadosAbiertos;
    private CandadosRepository candadosRepo;

    public InstruccionesDevViewModel(EstacionesRepository estacionesRepo, CandadosRepository
            candadosRepository) {
        this.estacionesRepo = estacionesRepo;
        this.estaciones = estacionesRepo.getData();

        this.candadosRepo = candadosRepository;
        this.candadosAbiertos = this.candadosRepo.getData();
    }

    //Para llamar
    public void obtenerEstacionesX() {
        estaciones = estacionesRepo.getEstaciones();
    }
    public void obtenerCandadosAbiertos(String idEstacion){
        candadosAbiertos = candadosRepo.getCandadosAbiertos(idEstacion);
    }

    //Para observar
    public LiveData<List<Estacion>> getEstaciones() {
        return this.estaciones;
    }
    public LiveData<List<Candado>> getCandadosAbiertos(){
        return this.candadosAbiertos;
    }

}
