package vizual.testapiv2;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import vizual.geolocation.GPSManager;
import vizual.geolocation.LocationManager;
import vizual.geolocation.OnGPSEnabledListener;
import vizual.geolocation.OnLocationListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private LocationManager locationManager;

    private Location mUserLocation;

    private LocationManager mLocationManager;
    private GPSManager mGPSManager;

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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

        //mMap.addMarker(new MarkerOptions()
          //      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
          //      .position(mapCenter)
          //      .flat(false));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(16)
                .bearing(0)
                .build();

        // Animate the change in camera view over 2 seconds
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                500, null);

        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(44.855157, -0.567730))
                .add(new LatLng(44.855286, -0.567542))  // North of the previous point, but at the same longitude
                .add(new LatLng(44.855157, -0.567392))  // Same latitude, and 30km to the west
                .add(new LatLng(44.855031, -0.567574))  // Same longitude, and 16km to the south
                .add(new LatLng(44.855157, -0.567730)); // Closes the polyline.
         Polyline polyline = mMap.addPolyline(rectOptions);
        //Affichage du premier carré !!
    }
}