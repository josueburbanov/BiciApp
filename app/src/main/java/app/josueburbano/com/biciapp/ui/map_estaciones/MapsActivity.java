package app.josueburbano.com.biciapp.ui.map_estaciones;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Estacion;
import app.josueburbano.com.biciapp.ui.MisReservas.MisReservasFragment;
import app.josueburbano.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity;
import app.josueburbano.com.biciapp.ui.login.LoginClienteView;

import java.util.List;

import static app.josueburbano.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, NavigationView.OnNavigationItemSelectedListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String ESTACION_VIEW = "app.josueburbano.com.biciapp.ESTACION";
    private double longitude;
    private double latitude;
    private GoogleMap mMap;
    protected LocationManager locationManager;
    private MapsViewModel viewModel;
    private LoginClienteView clienteView;

    private ImageView estacion_icon;
    private TextView titulo;
    private TextView texto;
    private View mapa;

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();



        //Obtener el usuario proveniente de la activity anterior (Login)
        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);


        //Coloca el mapa en la posición actual del teléfono y chequea permisos
        setMapsOnCurrentLocation();



        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MisReservasFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_message);

        }


        viewModel = ViewModelProviders.of(this, new MapsViewModelFactory())
                .get(MapsViewModel.class);

        //Retrieves List<Estacion> from WCF
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MisReservasFragment()).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(getApplicationContext(), "Share pushed", Toast.LENGTH_LONG).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

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
        //mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(new InfoAdapter(getLayoutInflater()));
        colocarEstacionEnMapa(null);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (!marker.getTitle().equals("Tú estás aquí")) {
                    Intent intent = new Intent(getApplicationContext(), EstacionBicicletasActivity.class);
                    intent.putExtra(CLIENT_VIEW, clienteView);
                    intent.putExtra(ESTACION_VIEW, (Estacion) marker.getTag());
                    startActivity(intent);
                }
            }
        });
    }

    private void colocarEstacionEnMapa(Estacion estacion) {

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
            amarker.setSnippet(estacion.getDireccion());
            //amarker.showInfoWindow();
            amarker.setTag(estacion);
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
        Log.d("TAG","onLocationChanged"+amarker.getTitle());
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
