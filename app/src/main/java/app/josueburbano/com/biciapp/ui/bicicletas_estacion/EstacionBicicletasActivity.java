package app.josueburbano.com.biciapp.ui.bicicletas_estacion;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.ui.login.LoginClienteView;
import app.josueburbano.com.biciapp.ui.reserva.ReservaActivity;

import static app.josueburbano.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;
import static app.josueburbano.com.biciapp.ui.map_estaciones.MapsActivity.ESTACION_VIEW;

public class EstacionBicicletasActivity extends AppCompatActivity {

    public static final String BICICLETA_VIEW = "app.josueburbano.com.biciapp.BICICLETA";
    private LoginClienteView clienteView;
    private Estacion estacionView;
    private EstacionBicicletasViewModel viewModel;

    ArrayAdapter<Bicicleta> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_bicicletas);

        //Obtener los objetos provenientes de la activity anterior (MapsActivity)
        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);
        estacionView = (Estacion) intent.getSerializableExtra(ESTACION_VIEW);

        final TextView titleEstationTextView = findViewById(R.id.titulo_estacion);
        final ListView bicisEstacionListView = findViewById(R.id.bicis_estacion);

        titleEstationTextView.setText(estacionView.getNombre());


        viewModel = ViewModelProviders.of(this, new EstacionBicicletasViewModelFactory())
                .get(EstacionBicicletasViewModel.class);

        viewModel.obtenerBicicletas(estacionView.getId());

        viewModel.getBicicletas().observe(this, new Observer<List<Bicicleta>>() {
            @Override
            public void onChanged(@Nullable List<Bicicleta> bicicletas) {
                adapter = new ArrayAdapter<Bicicleta>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, new ArrayList<Bicicleta>(bicicletas));
                bicisEstacionListView.setAdapter(adapter);
            }
        });

        bicisEstacionListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                Bicicleta item = (Bicicleta) adapter.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), ReservaActivity.class);
                intent.putExtra(BICICLETA_VIEW, item);
                intent.putExtra(ESTACION_VIEW, estacionView);
                intent.putExtra(CLIENT_VIEW, clienteView);
                startActivity(intent);
            }
        });
    }
}
