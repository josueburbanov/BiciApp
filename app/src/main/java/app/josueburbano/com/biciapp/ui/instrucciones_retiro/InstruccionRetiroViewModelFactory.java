package app.josueburbano.com.biciapp.ui.instrucciones_retiro;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp.datos.repos.BicisCandadosRepository;
import app.josueburbano.com.biciapp.datos.repos.CandadosRepository;
import app.josueburbano.com.biciapp.datos.repos.ReservasRepository;

public class InstruccionRetiroViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InstruccionesRetiroViewModel.class)) {
            return (T) new InstruccionesRetiroViewModel(new BicisCandadosRepository(),
                    new CandadosRepository(), new ReservasRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
