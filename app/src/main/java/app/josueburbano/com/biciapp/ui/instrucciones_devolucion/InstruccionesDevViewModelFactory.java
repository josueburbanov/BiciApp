package app.josueburbano.com.biciapp.ui.instrucciones_devolucion;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.CandadosRepository;
import app.josueburbano.com.biciapp.datos.EstacionesRepository;

public class InstruccionesDevViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InstruccionesDevViewModel.class)) {
            return (T) new InstruccionesDevViewModel(new EstacionesRepository(), new CandadosRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}