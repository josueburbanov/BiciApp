package app.admin.com.biciapp.ui.instrucciones_devolucion;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.admin.com.biciapp.datos.repos.CandadosRepository;
import app.admin.com.biciapp.datos.repos.EstacionesRepository;

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