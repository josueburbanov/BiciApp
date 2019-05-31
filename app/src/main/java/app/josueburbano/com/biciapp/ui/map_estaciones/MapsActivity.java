package app.josueburbano.com.biciapp.ui.map_estaciones;

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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity;
import app.josueburbano.com.biciapp.ui.login.LoginClienteView;

import java.util.List;

import static app.josueburbano.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String ESTACION_VIEW = "app.josueburbano.com.biciapp.ESTACION";
    private double longitude;
    private double latitude;
    private GoogleMap mMap;
    protected LocationManager locationManager;
    private MapsViewModel viewModel;
    private LoginClienteView clienteView;
    private Estacion estacionSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Obtener el usuario proveniente de la activity anterior (Login)
        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);

        //Coloca el mapa en la posición actual del teléfono y chequea permisos
        setMapsOnCurrentLocation();


        viewModel = ViewModelProviders.of(this, new MapsViewModelFactory())
                .get(MapsViewModel.class);

        //Retrieve List<Estacion> from WCF
        viewModel.obtenerEstacionesX();


        viewModel.getEstaciones().observe(this, new Observer<List<Estacion>>() {
            @Override
            public void onChanged(@Nullable List<Estacion> estaciones) {
                //Coloca el pin para cada estación
                for (Estacion estacion : estaciones) {
                    colocarEstacionEnMapa(estacion);
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        colocarEstacionEnMapa(null);
    }

    private void colocarEstacionEnMapa(Estacion estacion) {
        LatLng posicion;
        if (estacion == null) {
            posicion = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(posicion).title("Tú estás aquí")).showInfoWindow();
            ;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
        } else {
            posicion = new LatLng(estacion.getLatitud(), estacion.getLongitud());
            Marker amarker = mMap.addMarker(new MarkerOptions().position(posicion).title("Estación: " + estacion.getNombre()));
            amarker.showInfoWindow();
            amarker.setTag(estacion);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle() != "Tú estás aquí") {
            Intent intent = new Intent(this, EstacionBicicletasActivity.class);
            intent.putExtra(CLIENT_VIEW, clienteView);
            intent.putExtra(ESTACION_VIEW, (Estacion) marker.getTag());
            startActivity(intent);
            return false;
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        LatLng posicionActual = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(posicionActual).title("Tú estás aquí")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(200.0f));
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
                                ActivityCompat.requestPermissions(MapsActivity.this,
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
}
