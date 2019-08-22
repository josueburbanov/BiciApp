package app.josueburbano.com.biciapp.ui.instrucciones_retiro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp.datos.BicisCandadosRepository;
import app.josueburbano.com.biciapp.datos.CandadosRepository;
import app.josueburbano.com.biciapp.datos.ReservasRepository;
import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp.datos.modelos.Candado;
import app.josueburbano.com.biciapp.datos.modelos.Reserva;

public class InstruccionesRetiroViewModel extends ViewModel {
    private LiveData<BiciCandado> biciEstacion;
    private BicisCandadosRepository biciEstacionRepo;
    private CandadosRepository candadosRepository;
    private ReservasRepository reservasRepository;
    private LiveData<Candado> candado;
    private LiveData<Reserva> reserva;
    private LiveData<Reserva> reservaCancelada;


    public InstruccionesRetiroViewModel(BicisCandadosRepository biciEstacionRepo,
                                        CandadosRepository candadosRepository,
                                        ReservasRepository reservasRepository) {
        this.biciEstacionRepo = biciEstacionRepo;
        this.biciEstacion = biciEstacionRepo.getData();


        this.candadosRepository = candadosRepository;
        this.candado = candadosRepository.getCandado();

        this.reservasRepository = reservasRepository;
        this.reserva = reservasRepository.getData();
        this.reservaCancelada = reservasRepository.getData();
    }

    //Para llamar
    public void obtenerBiciEstacion(String idBici) {
        biciEstacion = biciEstacionRepo.getBiciEstacion(idBici, null);
    }
    public void obtenerCandado(String idCandado){
        candado = candadosRepository.obtenerCandado(idCandado);
    }
    public void cancelarReserva(String idReserva){
        reservaCancelada = reservasRepository.cancelarReserva(idReserva);
    }
    public void obtenerReservaActiva(String idCliente){
        reserva = reservasRepository.obtenerReservaActiva(idCliente);
    }


    //Para observar
    public LiveData<BiciCandado> getBiciEstacion() {
        return this.biciEstacion;
    }
    public LiveData<Candado> getCandado() {return this.candado;}
    public LiveData<Reserva> getReserva(){return this.reserva;}
    public LiveData<Reserva> getReservaCancelada(){return this.reservaCancelada;}

}
