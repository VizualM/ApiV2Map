package vizual.geolocation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context mContext;

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    private OnLocationListener mOnLocationListener;

    private static LocationManager instance;
    private static final Object lock = new Object();

    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(1000)
            .setFastestInterval(16)
            .setSmallestDisplacement((float) 0.5)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    public static LocationManager getInstance(Context context){
        synchronized (lock){
            if(instance == null)
                instance = new LocationManager(context);
        }

        instance.mContext = context;

        return instance;
    }

    private LocationManager(Context context){
        this.mContext = context;
        buildGoogleApiClient(mContext);
    }

    protected synchronized void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null && mOnLocationListener != null)
            mOnLocationListener.onLocationFound(mLastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void launchGeolocation(){
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    public void onStop(){
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    public void setOnLocationListener(OnLocationListener listener){
        mOnLocationListener = listener;
    }
    public void onChanged(){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null && mOnLocationListener != null)
            mOnLocationListener.onLocationFound(mLastLocation);
    }
}
