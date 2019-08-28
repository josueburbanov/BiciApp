package app.josueburbano.com.biciapp.ui.procesando_retiro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Candado;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.datos.modelos.Reserva;
import app.josueburbano.com.biciapp.ui.RodandoBiciActivity;
import app.josueburbano.com.biciapp.ui.instrucciones_retiro.InstruccionRetiroViewModelFactory;
import app.josueburbano.com.biciapp.ui.instrucciones_retiro.InstruccionesRetiroViewModel;
import app.josueburbano.com.biciapp.ui.login.LoginClienteView;
import app.josueburbano.com.biciapp.ui.main.MainActivity;
import app.josueburbano.com.biciapp.ui.procesando_entrega.ProcesandoEntregaViewModel;
import app.josueburbano.com.biciapp.ui.procesando_entrega.ProcesandoEntregaViewModelFactory;
import app.josueburbano.com.biciapp.ui.reserva.ReservaViewModel;
import app.josueburbano.com.biciapp.ui.reserva.ReservaViewModelFactory;

import static app.josueburbano.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity.BICICLETA_VIEW;
import static app.josueburbano.com.biciapp.ui.instrucciones_devolucion.InstruccionesDevolucionActivity.CANDADO_VIEW;
import static app.josueburbano.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;
import static app.josueburbano.com.biciapp.ui.map_estaciones.MapsActivity.ESTACION_VIEW;
import static app.josueburbano.com.biciapp.ui.reserva.ReservaActivity.RESERVA_VIEW;

public class ProcesandoRetiroActivity extends AppCompatActivity {

    private LoginClienteView clienteView;
    private Candado candadoView;
    private Bicicleta bicicletaView;
    ProcesandoEntregaViewModel viewModel;
    private Estacion estacionView;
    private Reserva reservaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesando_retiro);

        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);
        bicicletaView = (Bicicleta) intent.getSerializableExtra(BICICLETA_VIEW);
        estacionView = (Estacion) intent.getSerializableExtra(ESTACION_VIEW);
        reservaView = (Reserva) intent.getSerializableExtra(RESERVA_VIEW);

        viewModel = ViewModelProviders.of(this, new ProcesandoEntregaViewModelFactory())
                .get(ProcesandoEntregaViewModel.class);

        viewModel.checkLastTransaccion(bicicletaView.getId());
        viewModel.getLastTransaccion().observe(this, new Observer<BiciCandado>() {
            @Override
            public void onChanged(@Nullable BiciCandado biciCandado) {
                if (biciCandado != null && biciCandado.getEntregaRetiro() && biciCandado.getError() == null) {

                    BiciCandado transaccionParcial = new BiciCandado();
                    transaccionParcial.setError("Retiro parcial");
                    //xxxxxxxxxxxxxxxxxx
                    transaccionParcial.setIdCandado("canda01");
                    //xxxxxxxxxxxxxxxxxx
                    transaccionParcial.setEntregaRetiro(true);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String sdt = df.format(new Date(System.currentTimeMillis()));
                    transaccionParcial.setFechaHora(sdt);
                    transaccionParcial.setFechaHora(sdt);
                    transaccionParcial.setIdBici(bicicletaView.getId());
                    viewModel.realizarTransaccionParcial(transaccionParcial);
                    viewModel.getLastTransaccion().removeObserver(this);
                }else{
                    handler.postDelayed(runnable = new Runnable() {
                        public void run() {
                            intetar_retirar_bici();
                            contador++;
                            if (contador >= 30) {
                                handler.removeCallbacks(runnable); //parar el handler cuando ha intentando por un tiempo
                                Toast.makeText(getApplicationContext(), "No se ha podido retirar la bicicleta", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            handler.postDelayed(runnable, delay);
                        }
                    }, delay);
                }
            }
        });


        viewModel.getTransaccion().observe(this, new Observer<BiciCandado>() {
            @Override
            public void onChanged(@Nullable BiciCandado biciCandado) {
                if (biciCandado != null) {
                    if (biciCandado.getError() != null || biciCandado.getEntregaRetiro()) {
                        handler.postDelayed(runnable = new Runnable() {
                            public void run() {
                                intetar_retirar_bici();
                                contador++;
                                if (contador >= 30) {
                                    handler.removeCallbacks(runnable); //parar el handler cuando ha intentando por un tiempo
                                    Toast.makeText(getApplicationContext(), "No se ha podido retirar la bicicleta", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                handler.postDelayed(runnable, delay);
                            }
                        }, delay);

                    }
                }
            }
        });
    }


    Handler handler = new Handler();
    Runnable runnable;
    int delay = 2 * 1000; //Delay for 6 seconds.  One second = 1000 milliseconds.
    int contador = 0;


    private void intetar_retirar_bici() {
        InstruccionesRetiroViewModel viewModel = ViewModelProviders.of(this, new InstruccionRetiroViewModelFactory())
                .get(InstruccionesRetiroViewModel.class);
        viewModel.obtenerBiciEstacion(bicicletaView.getId());
        viewModel.getBiciEstacion().observe(this, new Observer<BiciCandado>() {
            @Override
            public void onChanged(@Nullable BiciCandado biciCandado) {
                if(biciCandado!= null) {
                    if (!biciCandado.getEntregaRetiro() && biciCandado.getError() == null && biciCandado.getStatusEntregaRecepcion()) {
                        Toast.makeText(getApplicationContext(), "Bicicleta retirada con éxito!", Toast.LENGTH_LONG).show();
                        handler.removeCallbacks(runnable);
                        Intent intent = new Intent(getApplicationContext(), RodandoBiciActivity.class);
                        intent.putExtra(ESTACION_VIEW, estacionView);
                        intent.putExtra(CLIENT_VIEW, clienteView);
                        intent.putExtra(RESERVA_VIEW,  reservaView);
                        intent.putExtra(BICICLETA_VIEW, bicicletaView);
                        startActivity(intent);
                    }
                }
            }
        });
    }


// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }


}
