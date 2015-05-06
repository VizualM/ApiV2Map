package vizual.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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

import vizual.dal.GeoPoint;
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

        //parse & download json
        ArrayList<Tile> TilesOnScreen = tilesController.Parse("127.0.0.1", "-0.590987", "44.857947", "-0.565238", "44.846233");

        for(int i = 0; i < TilesOnScreen.size(); i++){

            double opacity =0.25;
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
        /*//Affichage du premier carré !!
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(44.855157, -0.567730)) //Start Point
                .add(new LatLng(44.855286, -0.567544)) // North of the previous point, but at the same longitude
                .add(new LatLng(44.855157, -0.567392)) // Same latitude
                .add(new LatLng(44.855031, -0.567574)) // Same longitude
                .add(new LatLng(44.855157, -0.567730)) // Closes the polyline.
                .color(Color.GREEN)
                .width(5);
        Polyline polyline = mMap.addPolyline(rectOptions);*/

        }

    }
        /*


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


