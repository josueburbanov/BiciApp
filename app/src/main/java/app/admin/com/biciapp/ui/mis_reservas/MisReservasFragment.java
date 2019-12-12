package app.admin.com.biciapp.ui.mis_reservas;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.admin.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity;
import app.admin.com.biciapp.ui.instrucciones_retiro.InstruccionesRetiroActivity;
import app.admin.com.biciapp.ui.map_estaciones.MapsActivity;
import app.josueburbano.com.biciapp.R;
import app.admin.com.biciapp.datos.modelos.Bicicleta;
import app.admin.com.biciapp.datos.modelos.Estacion;
import app.admin.com.biciapp.datos.modelos.Reserva;
import app.admin.com.biciapp.ui.login.LoginClienteView;

import static app.admin.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;
import static app.admin.com.biciapp.ui.reserva.ReservaActivity.RESERVA_VIEW;

public class MisReservasFragment extends Fragment {

    private ListView listView;
    private MisReservasViewModel viewModel;
    private LoginClienteView clienteView;
    private ArrayAdapter<Reserva> adapter;
    private Bicicleta bicicletaView;
    private Reserva reservaView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mis_reservas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView) getView().findViewById(R.id.listViewReservas);

        Intent intent = getActivity().getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);

        viewModel = ViewModelProviders.of(this, new MisReservasViewModelFactory())
                .get(MisReservasViewModel.class);
        viewModel.obtenerReservas(clienteView.getId());
        viewModel.getReservas().observe(this, new Observer<List<Reserva>>() {
            @Override
            public void onChanged(@Nullable List<Reserva> reservas) {
                if(reservas!=null){
                adapter = new ArrayAdapter<Reserva>(getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1, new ArrayList<Reserva>(reservas));
                listView.setAdapter(adapter);
                }
            }
        });

        viewModel.getBicicleta().observe(this, new Observer<Bicicleta>() {
            @Override
            public void onChanged(@Nullable Bicicleta bicicleta) {
                if(bicicleta!= null){
                    bicicletaView = bicicleta;
                    viewModel.obtenerEstacion(bicicleta.getId());
                }
            }
        });

        viewModel.getEstacion().observe(this, new Observer<Estacion>() {
            @Override
            public void onChanged(@Nullable Estacion estacion) {
                if(estacion != null){
                    Intent intent = new Intent(getActivity().getApplicationContext(), InstruccionesRetiroActivity.class);
                    intent.putExtra(RESERVA_VIEW,  reservaView);
                    intent.putExtra(EstacionBicicletasActivity.BICICLETA_VIEW, bicicletaView);
                    intent.putExtra(MapsActivity.ESTACION_VIEW, estacion);
                    intent.putExtra(CLIENT_VIEW, clienteView);
                    startActivity(intent);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                Reserva item = (Reserva) adapter.getItemAtPosition(position);
                if(item.isActiva()){
                    reservaView = item;
                    viewModel.obtenerBicicleta(item.getIdBici());
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Solo se puede acceder a reservas activas.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
