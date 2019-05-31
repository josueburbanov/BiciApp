package app.josueburbano.com.biciapp.ui.bicicletas_estacion;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.josueburbano.com.biciapp.datos.BicicletasRepository;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;

public class EstacionBicicletasViewModel extends ViewModel {
    private LiveData<List<Bicicleta>> bicicletas;
    private BicicletasRepository bicicletasRepo;

    public EstacionBicicletasViewModel(BicicletasRepository bicicletasRepo) {
        this.bicicletasRepo = bicicletasRepo;
        this.bicicletas = bicicletasRepo.getData();
    }

    //Para llamar
    public void obtenerBicicletas(String idEstacion) {
        bicicletas = bicicletasRepo.getBicicletas(idEstacion);
    }

    //Para observar
    public LiveData<List<Bicicleta>> getBicicletas() {
        return this.bicicletas;
    }

}
