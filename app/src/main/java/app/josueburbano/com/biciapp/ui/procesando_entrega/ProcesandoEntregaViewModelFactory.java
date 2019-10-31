package app.josueburbano.com.biciapp.ui.procesando_entrega;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.repos.BicisCandadosRepository;

public class ProcesandoEntregaViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProcesandoEntregaViewModel.class)) {
            return (T) new ProcesandoEntregaViewModel(new BicisCandadosRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
