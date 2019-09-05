package app.josueburbano.com.biciapp.ui.instrucciones_devolucion;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp.datos.modelos.Candado;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.datos.modelos.Reserva;
import app.josueburbano.com.biciapp.ui.procesando_entrega.Procesando_entrega;
import app.josueburbano.com.biciapp.ui.login.LoginClienteView;
import app.josueburbano.com.biciapp.ui.map_estaciones.InfoAdapter;
import app.josueburbano.com.biciapp.ui.reserva.ReservaViewModel;
import app.josueburbano.com.biciapp.ui.reserva.ReservaViewModelFactory;

import static app.josueburbano.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity.BICICLETA_VIEW;
import static app.josueburbano.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;

public class InstruccionesDevolucionActivity extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String ESTACION_VIEW = "app.josueburbano.com.biciapp.ESTACION";
    private double longitude;
    private double latitude;
    private GoogleMap mMap;
    protected LocationManager locationManager;
    private InstruccionesDevViewModel viewModel;
    private LoginClienteView clienteView;
    MapView mMapView;
    TextView textViewCandado;
    TextView textViewInstruccionFinal;
    Button btnFinalizar;


    private List<Estacion> estacionesField;
    private Candado candadoView;
    public static final String CANDADO_VIEW = "app.josueburbano.com.biciapp.CANDADO";
    private Bicicleta bicicletaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones_devolucion);


        mMapView = (MapView) findViewById(R.id.mapViewDev);
        textViewCandado = (TextView) findViewById(R.id.textViewCandado);
        textViewInstruccionFinal = (TextView) findViewById(R.id.textViewInstruccionFinal);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);


        textViewCandado.setVisibility(View.INVISIBLE);
        textViewInstruccionFinal.setVisibility(View.INVISIBLE);
        btnFinalizar.setEnabled(false);


        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setInfoWindowAdapter(new InfoAdapter(getLayoutInflater()));
                colocarEstacionEnMapa(null, null);
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if (!marker.getTitle().equals("Tú estás aquí")) {
                            //if(checkMyLocationEstacion((Estacion)marker.getTag())){
                            //}
                            textViewCandado.setVisibility(View.VISIBLE);
                            textViewCandado.setText("Ubique la bicicleta dentro del candado: " +
                                    ((Candado) marker.getTag()).getId());
                            candadoView = (Candado) marker.getTag();
                            textViewInstruccionFinal.setVisibility(View.VISIBLE);
                            btnFinalizar.setEnabled(true);
                        }
                    }
                });

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        mMap.clear();
                        LatLng posicionActual = new LatLng(latitude, longitude);
                        Marker amarker = mMap.addMarker(new MarkerOptions().position(posicionActual).title("Tú estás aquí"));
                        amarker.showInfoWindow();
                        Log.d("TAG", "onLocationChanged" + amarker.getTitle());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
                    }
                });
            }
        });

        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);
        bicicletaView = (Bicicleta) intent.getSerializableExtra(BICICLETA_VIEW);


        //Coloca el mapa en la posición actual del teléfono y chequea permisos
        setMapsOnCurrentLocation();

        viewModel = ViewModelProviders.of(this, new InstruccionesDevViewModelFactory())
                .get(InstruccionesDevViewModel.class);

        //Retrieves List<Estacion> from WCF
        viewModel.obtenerEstacionesX();


        viewModel.getCandadosAbiertos().observe(this, new Observer<List<Candado>>() {
            @Override
            public void onChanged(@Nullable List<Candado> candados) {

                if (candados != null ) {
                    if (candados.size() != 0 && candados.get(0)!= null) {
                        for (Estacion estacion : estacionesField) {
                            if (estacion.getId().equals(candados.get(0).getIdEstacion())) {
                                colocarEstacionEnMapa(estacion, candados);
                            }
                        }
                    }
                }
            }
        });

        viewModel.getEstaciones().observe(this, new Observer<List<Estacion>>() {
            @Override
            public void onChanged(@Nullable List<Estacion> estaciones) {
                //Coloca el pin para cada estación
                estacionesField = estaciones;
                for (Estacion estacion : estaciones) {
                    viewModel.obtenerCandadosAbiertos(estacion.getId());
                }
            }
        });
    }

    private void setMapsOnCurrentLocation() {
        //Se chequea permisos de uso del GPS
        checkLocationPermission();

        //Aqui se almacenará la última posición conocida
        Location location;

        //Se chequea que se tenga acceso a internet
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {

            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        }
    }

    private boolean checkMyLocationEstacion(Estacion estacion) {
        return latitude - 0.0001 < estacion.getLatitud() && estacion.getLatitud() < latitude + 0.0001
                && longitude - 0.0001 < estacion.getLongitud() && estacion.getLongitud() < longitude + 0.001;
    }

    private void colocarEstacionEnMapa(Estacion estacion, List<Candado> candadosEstacion) {
        if (estacion == null) {
            LatLng posicion = new LatLng(latitude, longitude);
            Marker amarker = mMap.addMarker(new MarkerOptions().position(posicion).title("Tú estás aquí"));
            amarker.setTitle("Tú estás aquí");
            mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
            amarker.showInfoWindow();
        } else {
            LatLng posicion = new LatLng(estacion.getLatitud(), estacion.getLongitud());
            Marker amarker = mMap.addMarker(new MarkerOptions().position(posicion).title("Estación: " + estacion.getNombre()));
            amarker.setTitle("Estación: " + estacion.getNombre());
            amarker.setSnippet(estacion.getDireccion() + "\nCandados disponibles: " + candadosEstacion.size());
            amarker.setTag(candadosEstacion.get(0));
        }
    }

    /*@Override
    public boolean onMarkerClick(Marker marker) {
            if (!marker.getTitle().equals("Tú estás aquí")) {
                Log.d("TAG","Entra a tu ");
                Intent intent = new Intent(this, EstacionBicicletasActivity.class);
                intent.putExtra(CLIENT_VIEW, clienteView);
                intent.putExtra(ESTACION_VIEW, (Estacion) marker.getTag());
                startActivity(intent);
                return false;
            }
        return true;
    }*/

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        LatLng posicionActual = new LatLng(latitude, longitude);
        Marker amarker = mMap.addMarker(new MarkerOptions().position(posicionActual).title("Tú estás aquí"));
        amarker.showInfoWindow();
        Log.d("TAG", "onLocationChanged" + amarker.getTitle());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(InstruccionesDevolucionActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    public void FinalizarEntrega(View view) {
        Intent intent = new Intent(getApplicationContext(), Procesando_entrega.class);
        intent.putExtra(CLIENT_VIEW, clienteView);
        intent.putExtra(CANDADO_VIEW, candadoView);
        intent.putExtra(BICICLETA_VIEW, bicicletaView);
        startActivity(intent);
    }
}

