package app.admin.com.biciapp.ui.map_estaciones;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import app.admin.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity;
import app.josueburbano.com.biciapp.R;
import app.admin.com.biciapp.datos.modelos.Estacion;
import app.admin.com.biciapp.ui.CustomDialogFragment;
import app.admin.com.biciapp.ui.InformacionActivity;
import app.admin.com.biciapp.ui.login.LoginClienteView;

import static app.admin.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;

public class MapViewFragment extends Fragment implements LocationListener{
    MapView mMapView;
    private GoogleMap mMap;
    protected LocationManager locationManager;
    private double longitude;
    private double latitude;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String ESTACION_VIEW = "app.josueburbano.com.biciapp.ESTACION";
    private LoginClienteView clienteView;
    private MapsViewModel viewModel;


    public void metodoExcDialog1() {
        Intent openIntent = new Intent(getActivity(), InformacionActivity.class);
        startActivity(openIntent);
    }

    public void metodoExcDialog2() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"jotzu3@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Solicitud de tarjeta de identificación");
        i.putExtra(Intent.EXTRA_TEXT   , "Mi nombre es: "+ clienteView.getNombreDisplay()+" y deseo la emisión de mi tarjeta de usuario");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Solicitud enviada", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Obtener el usuario proveniente de la activity anterior (Login)
        Intent intent = getActivity().getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);

        if(clienteView.getRfid() == null){
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.setTitulo("Aún no tienes una tarjeta de usuario...");
            dialog.setMensaje("Para poder utilizar nuestros servicios es necesario tener una tarjeta de autenticación.");
            dialog.setMensajeBtnPositivo("Solicitar tarjeta");
            dialog.setMensajeBtnNegativo("Más información");
            dialog.objectCont = this;
            try {
                dialog.metodoBtnNegativo = ( MapViewFragment.class.getMethod("metodoExcDialog1"));
                dialog.setMetodoBtnPositivo( ( MapViewFragment.class.getMethod("metodoExcDialog2")));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            dialog.show(getFragmentManager(), "NoticeDialogFragment");
            dialog.setCancelable(false);
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setInfoWindowAdapter(new InfoAdapter(getLayoutInflater()));
                colocarEstacionEnMapa(null);
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if (!marker.getTitle().equals("Tú estás aquí")) {
                            Intent intent = new Intent(getContext(), EstacionBicicletasActivity.class);
                            intent.putExtra(CLIENT_VIEW, clienteView);
                            intent.putExtra(ESTACION_VIEW, (Estacion) marker.getTag());
                            startActivity(intent);
                        }
                    }
                });
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        mMap.clear();
                        LatLng posicionActual = new LatLng(latitude, longitude);
                        Marker amarker = mMap.addMarker(new MarkerOptions().position(posicionActual).title("Tú estás aquí"));
                        amarker.setIcon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_location_on_black_24dp));

                        amarker.showInfoWindow();
                        Log.d("TAG", "onLocationChanged" + amarker.getTitle());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
                    }
                });
            }
        });

        setMapsOnCurrentLocation();

        viewModel = ViewModelProviders.of(this, new MapsViewModelFactory())
                .get(MapsViewModel.class);

        //Retrieves List<Estacion> from WCF
        viewModel.obtenerEstacionesX();


        viewModel.getEstaciones().observe(this, new Observer<List<Estacion>>() {
            @Override
            public void onChanged(@Nullable List<Estacion> estaciones) {
                if(estaciones != null) {
                    //Coloca el pin para cada estación
                    for (Estacion estacion : estaciones) {
                        colocarEstacionEnMapa(estacion);
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Ha habido un problema" +
                            "con el servidor. No se han cargado estaciones.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    private void colocarEstacionEnMapa(Estacion estacion) {

        if (estacion == null) {
            LatLng posicion = new LatLng(latitude, longitude);
            Marker amarker = mMap.addMarker(new MarkerOptions().position(posicion).title("Tú estás aquí"));
            amarker.setTitle("Tú estás aquí");
            amarker.setIcon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_location_on_black_24dp));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
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

    private void setMapsOnCurrentLocation() {
        //Se chequea permisos de uso del GPS
        checkLocationPermission();

        //Aqui se almacenará la última posición conocida
        Location location;

        //Se chequea que se tenga acceso a internet
        LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {

            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
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
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, (LocationListener) this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        LatLng posicionActual = new LatLng(latitude, longitude);
        Marker amarker = mMap.addMarker(new MarkerOptions().position(posicionActual).title("Tú estás aquí"));
        amarker.showInfoWindow();
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

    @Override
    public void onResume() {
        super.onResume();
        if (clienteView.getRfid() == null) {
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.setTitulo("Aún no tienes una tarjeta de usuario...");
            dialog.setMensaje("Para poder utilizar nuestros servicios es necesario tener una tarjeta de autenticación.");
            dialog.setMensajeBtnPositivo("Solicitar tarjeta");
            dialog.setMensajeBtnNegativo("Más información");
            dialog.objectCont = this;
            try {
                dialog.metodoBtnNegativo = (MapViewFragment.class.getMethod("metodoExcDialog1"));
                dialog.setMetodoBtnPositivo((MapViewFragment.class.getMethod("metodoExcDialog2")));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            dialog.show(getFragmentManager(), "NoticeDialogFragment");
            dialog.setCancelable(false);
        }
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location_on_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
