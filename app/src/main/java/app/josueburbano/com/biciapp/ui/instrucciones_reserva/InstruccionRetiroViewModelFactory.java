package app.josueburbano.com.biciapp.ui.instrucciones_reserva;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.BicisCandadosRepository;

public class InstruccionRetiroViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InstruccionesRetiroViewModel.class)) {
            return (T) new InstruccionesRetiroViewModel(new BicisCandadosRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
