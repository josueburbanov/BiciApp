package app.josueburbano.com.biciapp.ui.registro;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.BicicletasRepository;
import app.josueburbano.com.biciapp.datos.ClienteRepository;
import app.josueburbano.com.biciapp.datos.EstacionesRepository;
import app.josueburbano.com.biciapp.datos.ReservasRepository;
import app.josueburbano.com.biciapp.ui.MisReservas.MisReservasViewModel;

public class RegistroViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistroViewModel.class)) {
            return (T) new RegistroViewModel(new ClienteRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
