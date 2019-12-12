package app.admin.com.biciapp.ui.reserva;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.admin.com.biciapp.datos.repos.ReservasRepository;

public class ReservaViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReservaViewModel.class)) {
            return (T) new ReservaViewModel(new ReservasRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}