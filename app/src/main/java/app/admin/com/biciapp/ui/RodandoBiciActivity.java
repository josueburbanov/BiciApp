package app.admin.com.biciapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.admin.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity;
import app.admin.com.biciapp.ui.instrucciones_devolucion.InstruccionesDevolucionActivity;
import app.admin.com.biciapp.ui.login.LoginActivity;
import app.admin.com.biciapp.ui.login.LoginClienteView;
import app.admin.com.biciapp.ui.reserva.ReservaActivity;
import app.josueburbano.com.biciapp.R;
import app.admin.com.biciapp.datos.modelos.Bicicleta;
import app.admin.com.biciapp.datos.modelos.Reserva;

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
        clienteView = (LoginClienteView) intent.getSerializableExtra(LoginActivity.CLIENT_VIEW);
        reservaView = (Reserva) intent.getSerializableExtra(ReservaActivity.RESERVA_VIEW);
        bicicletaView = (Bicicleta) intent.getSerializableExtra(EstacionBicicletasActivity.BICICLETA_VIEW);


        textViewHoraEntrega = (TextView) findViewById(R.id.textViewHoraEntrega);
        textViewHoraEntrega.setText(getString(R.string.entrega_bici) + " "+ reservaView.getHoraFin());
    }

    public void EntregarBici(View view){
        Intent intent = new Intent(getApplicationContext(), InstruccionesDevolucionActivity.class);
        intent.putExtra(LoginActivity.CLIENT_VIEW, clienteView);
        intent.putExtra(EstacionBicicletasActivity.BICICLETA_VIEW, bicicletaView);
        startActivity(intent);
    }
}
