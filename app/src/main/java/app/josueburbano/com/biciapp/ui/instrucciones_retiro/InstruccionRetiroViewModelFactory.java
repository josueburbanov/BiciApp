package app.josueburbano.com.biciapp.ui.instrucciones_retiro;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.BicisCandadosRepository;
import app.josueburbano.com.biciapp.datos.CandadosRepository;

public class InstruccionRetiroViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InstruccionesRetiroViewModel.class)) {
            return (T) new InstruccionesRetiroViewModel(new BicisCandadosRepository(), new CandadosRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
