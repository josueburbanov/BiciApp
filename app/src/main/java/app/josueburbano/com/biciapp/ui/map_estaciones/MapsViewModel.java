package app.josueburbano.com.biciapp.ui.map_estaciones;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.josueburbano.com.biciapp.datos.repos.EstacionesRepository;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;

public class MapsViewModel extends ViewModel {
    private LiveData<List<Estacion>> estaciones;
    private EstacionesRepository estacionesRepo;

    public MapsViewModel(EstacionesRepository estacionesRepo) {
        this.estacionesRepo = estacionesRepo;
        this.estaciones = estacionesRepo.getData();
    }

    //Para llamar
    public void obtenerEstacionesX() {
        estaciones = estacionesRepo.getEstaciones();
    }

    //Para observar
    public LiveData<List<Estacion>> getEstaciones() {
        return this.estaciones;
    }

}
