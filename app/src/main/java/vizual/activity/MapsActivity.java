package vizual.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import vizual.geolocation.GPSManager;
import vizual.geolocation.LocationManager;
import vizual.geolocation.OnGPSEnabledListener;
import vizual.geolocation.OnLocationListener;
import vizual.parsing.json.TilesController;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private LocationManager locationManager;

    private Location mUserLocation;
    private Marker myMarker;
    private LocationManager mLocationManager;
    private GPSManager mGPSManager;
    private TilesController tilesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mMap);

        mLocationManager = LocationManager.getInstance(this);
        mGPSManager = GPSManager.getInstance(this);

        mLocationManager.setOnLocationListener(new OnLocationListener() {
            @Override
            public void onLocationFound(Location location) {
                //Ici = localisation trouvée
                if(location != null) {
                    mUserLocation = location;
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });

        if(!mGPSManager.isGPSActivated()) {
            mGPSManager.askToActivateGPS(new OnGPSEnabledListener() {
                @Override
                public void onGPSEnabled(boolean enabled) {
                    //On arrive ici lorsque l'utilisateur décide d'activer ou non sont GPS
                    if(enabled)
                        mLocationManager.launchGeolocation();
                }
            });
        } else {
            //On arrive ici lorsque le GPS est déjà activé
            mLocationManager.launchGeolocation();
        }

        //parse & download json
        tilesController.init();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mGPSManager.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng mapCenter = new LatLng(mUserLocation.getLatitude(), mUserLocation.getLongitude());

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setIndoorEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

        //mMap.addMarker(new MarkerOptions()
          //      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
          //      .position(mapCenter)
          //      .flat(false));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(19)
                .bearing(0)
                .build();

        // Animate the change in camera view over 2 seconds
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                500, null); // Animation de la caméra

        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(44.855157, -0.567730)) //Start Point
                .add(new LatLng(44.855286, -0.567544)) // North of the previous point, but at the same longitude
                .add(new LatLng(44.855157, -0.567392)) // Same latitude
                .add(new LatLng(44.855031, -0.567574)) // Same longitude
                .add(new LatLng(44.855157, -0.567730)) // Closes the polyline.
                .color(Color.GREEN)
                .width(5);
        Polyline polyline = mMap.addPolyline(rectOptions);
        //Affichage du premier carré !!
        // --- Mise en place d'un algo de 100 carrés ---
                double a = 44.855333;
                double b = -0.567552;
                double c = 44.855332;
                double d = -0.567298;
                double e = 44.855514;
                double f = -0.567297;
                double g = 44.855513;
                double h = -0.567553;
                double j = 44.855333;
                double k = -0.567552;
                for (int i = 0; i < 1; i++) {
            /*PolylineOptions carreOptions = new PolylineOptions()
                    .add(new LatLng(a, b))
                    .add(new LatLng(c, d))
                    .add(new LatLng(e, f))
                    .add(new LatLng(g, h))
                    .add(new LatLng(j, k))
                    .width(6)
                    .color(Color.rgb(255, 83, 13));*/

            LatLng south = new LatLng(44.855157,  -0.567730);
            double opacity =0.25;

            GroundOverlay groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory
                            .fromResource(R.drawable.carte))
                    .position(south, 20f, 20f)
                    .anchor(0, 1)
                    .transparency((float) opacity));

            groundOverlay.setDimensions(20);


            b = b + (-0.000265);
            c = c + 0.00000;
            d = d + (-0.000265);
            e = e + 0.00000;
            f = f + (-0.000265);
            g = g + 0.00000;
            h = h + (-0.000265);
            j = j + 0.00000;
            k = k + (-0.000265);
            //Ajout de 20 metre supplémentaire

        }
    }
        /*
        public boolean onMarkerClick(final Marker marker) {

            if (marker.equals(myMarker)) {
                //handle click here
                myMarker.getTitle().equals("poto");
            }
            return true;

        }

        //GoogleMap.OnMapClickListener(44.855157, -0.567730);
        //Création d'un marker clickable
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng mapCenter) {
                Log.d("Map", "Map clicked");
                marker.remove();
                drawMarker(point);
            }
        });
*/
}
