package app.josueburbano.com.biciapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;

import static app.josueburbano.com.biciapp.ui.EstacionBicicletasActivity.BICICLETA_VIEW;
import static app.josueburbano.com.biciapp.ui.LoginActivity.CLIENT_VIEW;
import static app.josueburbano.com.biciapp.ui.MapsActivity.ESTACION_VIEW;

public class ReservaActivity extends AppCompatActivity {

    private LoginClienteView clienteView;
    private Estacion estacionView;
    private Bicicleta bicicletaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        //Obtener los objetos provenientes de la activity anterior (EstacionBicicletasActivty)
        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);
        estacionView = (Estacion) intent.getSerializableExtra(ESTACION_VIEW);
        bicicletaView = (Bicicleta) intent.getSerializableExtra(BICICLETA_VIEW);

        final TextView estacionTextView = findViewById(R.id.estacionTextView);
        final TextView bicicletaTextView = findViewById(R.id.bicicletaTextView);
        final Button reservarButton = findViewById(R.id.reservarButton);
        final ProgressBar loadingProgressBar = findViewById(R.id.reservarProgressBar);

        estacionTextView.setText(estacionView.getNombre());
        bicicletaTextView.setText(bicicletaView.getModelo());

        //El listener del tap del botón
        reservarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                //Solicita loguearse, se comunica con el repositorio
                viewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

    }
}
