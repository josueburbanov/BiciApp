package app.josueburbano.com.biciapp.ui.mis_reservas;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.josueburbano.com.biciapp.datos.repos.BicicletasRepository;
import app.josueburbano.com.biciapp.datos.repos.EstacionesRepository;
import app.josueburbano.com.biciapp.datos.repos.ReservasRepository;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.datos.modelos.Reserva;

public class MisReservasViewModel extends ViewModel {
    private LiveData<List<Reserva>> reservas;
    private ReservasRepository reservasRepo;
    private BicicletasRepository bicicletasRepository;
    private LiveData<Bicicleta> bicicletaReserva;
    private EstacionesRepository estacionesRepository;
    private LiveData<Estacion> estacionReserva;

    public MisReservasViewModel(ReservasRepository reservasRepo, BicicletasRepository
            bicicletasRepository, EstacionesRepository estacionesRepository) {
        this.reservasRepo = reservasRepo;
        this.reservas = this.reservasRepo.getReservas();
        this.bicicletasRepository = bicicletasRepository;
        this.bicicletaReserva = this.bicicletasRepository.getBicicleta();

        this.estacionesRepository = estacionesRepository;
        this.estacionReserva = this.estacionesRepository.getEstacion();
    }

    //Para llamar
    public void obtenerReservas(String idCliente) {
        reservas = reservasRepo.obtenerReservas(idCliente);
    }
    public void obtenerBicicleta(String idBici) {
        bicicletaReserva = bicicletasRepository.obtenerBicicleta(idBici);
    }
    public void obtenerEstacion(String idBici) {
        estacionReserva = estacionesRepository.getEstacionByBici(idBici);
    }



    //Para observar
    public LiveData<List<Reserva>> getReservas() {
        return this.reservas;
    }
    public LiveData<Bicicleta> getBicicleta() {
        return this.bicicletaReserva;
    }
    public LiveData<Estacion> getEstacion() {
        return this.estacionReserva;
    }
}
