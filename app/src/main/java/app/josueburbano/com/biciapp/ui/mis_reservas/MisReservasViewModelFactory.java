package app.josueburbano.com.biciapp.ui.mis_reservas;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.BicicletasRepository;
import app.josueburbano.com.biciapp.datos.EstacionesRepository;
import app.josueburbano.com.biciapp.datos.ReservasRepository;

public class MisReservasViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MisReservasViewModel.class)) {
            return (T) new MisReservasViewModel(new ReservasRepository(), new BicicletasRepository()
                    , new EstacionesRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
