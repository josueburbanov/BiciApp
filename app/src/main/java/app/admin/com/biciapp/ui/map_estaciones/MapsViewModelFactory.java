package app.admin.com.biciapp.ui.map_estaciones;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.admin.com.biciapp.datos.repos.EstacionesRepository;

public class MapsViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MapsViewModel.class)) {
            return (T) new MapsViewModel(new EstacionesRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
