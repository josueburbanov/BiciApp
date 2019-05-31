package app.josueburbano.com.biciapp.ui.reserva;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp.datos.ReservasRepository;
import app.josueburbano.com.biciapp.datos.modelos.Reserva;

public class ReservaViewModel extends ViewModel {

    //Commited Reserva
    private LiveData<Reserva> reservaCompletada;
    private ReservasRepository reservaRepo;

    public ReservaViewModel(ReservasRepository reservaRepo) {
        this.reservaRepo = reservaRepo;
        this.reservaCompletada = reservaRepo.getData();
    }

    //Para llamar, se envía reservaCreada (Uncommited Reserva, still in UI)
    public void makeReserva(Reserva reservaCreada) {
        reservaCompletada = reservaRepo.crearReserva(reservaCreada);
    }

    //Para observar
    public LiveData<Reserva> getReservaCompletada() {
        return this.reservaCompletada;
    }




}
