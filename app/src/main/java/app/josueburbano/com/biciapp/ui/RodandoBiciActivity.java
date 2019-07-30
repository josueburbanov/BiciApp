package app.josueburbano.com.biciapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Reserva;
import app.josueburbano.com.biciapp.ui.instrucciones_devolucion.InstruccionesDevolucionActivity;
import app.josueburbano.com.biciapp.ui.login.LoginClienteView;

import static app.josueburbano.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity.BICICLETA_VIEW;
import static app.josueburbano.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;
import static app.josueburbano.com.biciapp.ui.reserva.ReservaActivity.RESERVA_VIEW;

public class RodandoBiciActivity extends AppCompatActivity {

    private LoginClienteView clienteView;
    private Bicicleta bicicletaView;
    private Reserva reservaView;
    private TextView textViewHoraEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rodando_bici);

        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);
        reservaView = (Reserva) intent.getSerializableExtra(RESERVA_VIEW);
        bicicletaView = (Bicicleta) intent.getSerializableExtra(BICICLETA_VIEW);


        textViewHoraEntrega = (TextView) findViewById(R.id.textViewHoraEntrega);
        textViewHoraEntrega.setText(getString(R.string.entrega_bici) + " "+ reservaView.getHoraFin());
    }

    public void EntregarBici(View view){
        Intent intent = new Intent(getApplicationContext(), InstruccionesDevolucionActivity.class);
        intent.putExtra(CLIENT_VIEW, clienteView);
        intent.putExtra(BICICLETA_VIEW, bicicletaView);
        startActivity(intent);
    }
}
