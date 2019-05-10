package app.josueburbano.com.biciapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;

import static app.josueburbano.com.biciapp.ui.LoginActivity.CLIENT_VIEW;
import static app.josueburbano.com.biciapp.ui.MapsActivity.ESTACION_VIEW;

public class EstacionBicicletasActivity extends AppCompatActivity {

    private LoginClienteView clienteView;
    private Estacion estacionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_bicicletas);

        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);
        estacionView = (Estacion) intent.getSerializableExtra(ESTACION_VIEW);
    }
}
