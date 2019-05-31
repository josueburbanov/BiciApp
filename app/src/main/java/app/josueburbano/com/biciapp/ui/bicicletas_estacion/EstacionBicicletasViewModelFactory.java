package app.josueburbano.com.biciapp.ui.bicicletas_estacion;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.BicicletasRepository;

public class EstacionBicicletasViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstacionBicicletasViewModel.class)) {
            return (T) new EstacionBicicletasViewModel(new BicicletasRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
