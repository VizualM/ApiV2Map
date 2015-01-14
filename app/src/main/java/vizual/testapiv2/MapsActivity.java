package vizual.testapiv2;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

        // Flat markers will rotate when the map is rotated,
        // and change perspective when the map is tilted.
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(mapCenter)
                .flat(false));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(100)
                .bearing(90)
                .build();

        // Animate the change in camera view over 2 seconds
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);

    }
}