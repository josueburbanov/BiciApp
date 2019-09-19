package app.josueburbano.com.biciapp.ui.instrucciones_retiro;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Candado;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.datos.modelos.Reserva;
import app.josueburbano.com.biciapp.ui.RodandoBiciActivity;
import app.josueburbano.com.biciapp.ui.login.LoginClienteView;
import app.josueburbano.com.biciapp.ui.main.MainActivity;
import app.josueburbano.com.biciapp.ui.procesando_retiro.ProcesandoRetiroActivity;

import static app.josueburbano.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity.BICICLETA_VIEW;
import static app.josueburbano.com.biciapp.ui.instrucciones_devolucion.InstruccionesDevolucionActivity.CANDADO_VIEW;
import static app.josueburbano.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;
import static app.josueburbano.com.biciapp.ui.map_estaciones.MapsActivity.ESTACION_VIEW;
import static app.josueburbano.com.biciapp.ui.reserva.ReservaActivity.RESERVA_VIEW;

public class InstruccionesRetiroActivity extends AppCompatActivity {

    private Reserva reservaView;
    private Estacion estacionView;
    private Bicicleta bicicletaView;
    private LoginClienteView clienteView;
    private Candado candadoView;
    private InstruccionesRetiroViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro_instrucciones);

        //Obtener los objetos proveniente de la activity anterior (ReservaActivity)
        Intent intent = getIntent();
        reservaView = (Reserva) intent.getSerializableExtra(RESERVA_VIEW);
        estacionView = (Estacion) intent.getSerializableExtra(ESTACION_VIEW);
        bicicletaView = (Bicicleta) intent.getSerializableExtra(BICICLETA_VIEW);
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);

        final TextView step1TextView = findViewById(R.id.step1TextView);
        final TextView step2TextView = findViewById(R.id.step2TextView);
        final TextView step3TextView = findViewById(R.id.step3TextView);
        final TextView step4TextView = findViewById(R.id.step4TextView);
        final Button buttonCancelar = findViewById(R.id.btnCancelar);
        buttonCancelar.setEnabled(false);

        step1TextView.setText(getString(R.string.step_one_instructions_to) + estacionView.getNombre()
        + getString(R.string.ubicacion_estacion) + estacionView.getDireccion());

        step2TextView.setText(getString(R.string.step_two_instructions_to) + bicicletaView.getId() +
                getString(R.string.modelo_bicicleta) + bicicletaView.getModelo());

        step3TextView.setText(getString(R.string.step_3_instructions_to) + reservaView.getFecha() +
                getString(R.string.hora_entrega_bici) + " "+ reservaView.getHoraInicio());

        step4TextView.setText(getString(R.string.step_4_instructions_to) +  reservaView.getHoraFin());

        viewModel = ViewModelProviders.of(this, new InstruccionRetiroViewModelFactory())
                .get(InstruccionesRetiroViewModel.class);

        if(reservaView == null){
            viewModel.obtenerReservaActiva(clienteView.getId());
        }else{
            buttonCancelar.setEnabled(true);
        }

        viewModel.getReserva().observe(this, new Observer<Reserva>() {
            @Override
            public void onChanged(@Nullable Reserva reserva) {
                reservaView = reserva;
                buttonCancelar.setEnabled(true);
            }
        });

        if(candadoView == null){
            viewModel.obtenerCandadoByBici(bicicletaView.getId());

        }

        viewModel.getCandadoCerrado().observe(this, new Observer<Candado>() {
            @Override
            public void onChanged(@Nullable Candado candado) {
                candadoView = candado;
            }
        });



        viewModel.obtenerBiciEstacion(bicicletaView.getId());

        viewModel.getBiciEstacion().observe(this, new Observer<BiciCandado>() {
            @Override
            public void onChanged(@Nullable BiciCandado biciCandado) {
                if (biciCandado == null) {
                    Toast.makeText(getApplicationContext(), "Por favor pase su tarjeta por el lector y vuelva a hacer click el botón de retirar, asegúrese que su reserva coincida con la hora actual.", Toast.LENGTH_LONG).show();
                } else {
                    step4TextView.setText(getString(R.string.step_4_1_instructions_to) + biciCandado.getIdCandado()+
                            getString(R.string.step_4_2_instructions_to)+reservaView.getHoraFin());
                    if (!biciCandado.getEntregaRetiro()) {
                        viewModel.obtenerCandado(biciCandado.getIdCandado());

                    }else{
                        Toast.makeText(getApplicationContext(), "Por favor haga click en el botón Retirar y pase su tarjeta por el lector, asegúrese que su reserva coincida con la hora actual.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        viewModel.getCandado().observe(this, new Observer<Candado>() {
            @Override
            public void onChanged(@Nullable Candado candado) {
                if(candado == null){
                    Toast.makeText(getApplicationContext(), "Por favor haga click en el botón Retirar y pase su tarjeta por el lector, asegúrese que su reserva coincida con la hora actual.", Toast.LENGTH_LONG).show();
                }else{
                    if(candado.isAbierto()){
                    Toast.makeText(getApplicationContext(), "Candado abierto. La bicicleta está bajo su responsabilidad", Toast.LENGTH_LONG).show();
                    candadoView = candado;
                    Intent intent = new Intent(getApplicationContext(), RodandoBiciActivity.class);
                    intent.putExtra(ESTACION_VIEW, estacionView);
                    intent.putExtra(CLIENT_VIEW, clienteView);
                    intent.putExtra(RESERVA_VIEW,  reservaView);
                    intent.putExtra(BICICLETA_VIEW, bicicletaView);
                    intent.putExtra(CANDADO_VIEW, candadoView);
                    startActivity(intent);
                } else {
                        Toast.makeText(getApplicationContext(), "Por favor haga click en el botón Retirar y pase su tarjeta por el lector, asegúrese que su reserva coincida con la hora actual.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public void retirarBicicleta(View view){
        viewModel.obtenerBiciEstacion(bicicletaView.getId());
        Intent intent = new Intent(getApplicationContext(), ProcesandoRetiroActivity.class);
        intent.putExtra(CLIENT_VIEW, clienteView);
        intent.putExtra(BICICLETA_VIEW, bicicletaView);
        intent.putExtra(ESTACION_VIEW, estacionView);
        intent.putExtra(RESERVA_VIEW,  reservaView);
        intent.putExtra(BICICLETA_VIEW, bicicletaView);
        intent.putExtra(CANDADO_VIEW, candadoView);
        startActivity(intent);
    }

    public void CancelarReserva(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirmación")
                .setMessage("Seguro de eliminar la reserva"
                        + "\nFecha: "+reservaView.getFecha()+"\nHora inicio: " + reservaView.getHoraFin() + "\nHora Fin: " + reservaView.getHoraInicio())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.cancelarReserva(reservaView.getId());
                    }
                })
                .setNegativeButton("No", null)
                .show();

        viewModel.getReservaCancelada().observe(this, new Observer<Reserva>() {
            @Override
            public void onChanged(@Nullable Reserva reserva) {
                //if(reserva.getFecha().equals(reservaView.getFecha())&& reserva.getHoraInicio().equals(reservaView.getHoraInicio())&&
                //reserva.getHoraFin().equals(reservaView.getHoraFin())){
                if(reserva.getId().equals(reservaView.getId())){
                    Toast.makeText(getApplicationContext(), "Reserva cancelada con éxito", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(CLIENT_VIEW, clienteView);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Hubo un problema cancelando su reserva. Intente más tarde", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
