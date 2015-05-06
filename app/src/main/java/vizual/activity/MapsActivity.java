package vizual.activity;

        
        import android.location.Location;
        import android.os.Bundle;
        import android.support.v4.app.FragmentActivity;
        import android.widget.TextView;


        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.GroundOverlay;
        import com.google.android.gms.maps.model.GroundOverlayOptions;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;

        import java.util.ArrayList;
        import java.util.List;

        import vizual.dal.Tile;
        import vizual.geolocation.GPSManager;
        import vizual.geolocation.LocationManager;
        import vizual.geolocation.OnGPSEnabledListener;
        import vizual.geolocation.OnLocationListener;
        import vizual.parsing.json.TilesController;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private LocationManager locationManager;

    private Location mUserLocation;
    private LocationManager mLocationManager;
    private GPSManager mGPSManager;
    private TilesController tilesController;
    TextView tvLocInfo;

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
                if (location != null) {
                    mUserLocation = location;
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });

        if (!mGPSManager.isGPSActivated()) {
            mGPSManager.askToActivateGPS(new OnGPSEnabledListener() {
                @Override
                public void onGPSEnabled(boolean enabled) {
                    //On arrive ici lorsque l'utilisateur décide d'activer ou non sont GPS
                    if (enabled)
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
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));


        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(19)
                .bearing(0)
                .build();

        // Animate the change in camera view over 2 seconds
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                500, null); // Animation de la caméra

        //parse & download json
        ArrayList<Tile> TilesOnScreen = tilesController.Parse("127.0.0.1", "-0.590987", "44.857947", "-0.565238", "44.846233");

        for (int i = 0; i < TilesOnScreen.size(); i++) {

            double opacity = 0.25;
            Tile j = TilesOnScreen.get(i);
            final LatLng jLatLong = new LatLng(j.BottomLeft.getLat(), j.BottomLeft.getLong());

            GroundOverlay groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory
                            .fromResource(R.drawable.carte))
                    .position(jLatLong, 20f, 20f)
                    .anchor(0, 1)
                    .transparency((float) opacity));

            groundOverlay.setDimensions(20);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {


            @Override
            public void onMapClick(LatLng latLng) {


                TextView UpdatePosition = (TextView) findViewById(R.id.UpdatePosition);
                UpdatePosition.setText("Latitude : " + latLng.latitude + "Longitude : " + latLng.longitude);
            }


            public void onMapClick(LatLng south, List<Tile> myTiles) {

                mMap.animateCamera(CameraUpdateFactory.newLatLng(south));

                int i = 0;
                while (true) {
                    if (south.latitude < myTiles.get(i).getBottomLeft().getLat() && south.longitude < myTiles.get(i).getBottomRight().getLong()) {

                        Tile myTile = myTiles.get(i);

                    } else {
                        i++;
                    }
                }
            }

        });

    }
}
