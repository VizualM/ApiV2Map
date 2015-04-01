package vizual.geolocation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;

import vizual.activity.R;

public class GPSManager {

    private static GPSManager instance;
    private static final Object lock = new Object();

    private Context mContext;
    private LocationManager mLocationManager;

    private boolean mGPSIsActivated;

    private OnGPSEnabledListener mOnGPSEnabledListener;

    public static GPSManager getInstance(Context context){
        synchronized (lock){
            if(instance == null)
                instance = new GPSManager(context);
        }

        instance.mContext = context;

        return instance;
    }

    public GPSManager(Context context){
        this.mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        mGPSIsActivated = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean isGPSActivated(){
        return mGPSIsActivated;
    }

    public void askToActivateGPS(OnGPSEnabledListener onGPSEnabledListener){
        this.mOnGPSEnabledListener = onGPSEnabledListener;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(mContext.getString(R.string.gps))
                .setMessage(mContext.getString(R.string.gps_disabled))
                .setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.gps_enable), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(gpsOptionsIntent);
                    }
                });

        builder.setNegativeButton(mContext.getString(R.string.gps_do_nothing), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                if(mOnGPSEnabledListener != null)
                    mOnGPSEnabledListener.onGPSEnabled(false);
            }
        });

        AlertDialog alert = builder.create();

        if(!((Activity) mContext).isFinishing())
            alert.show();
    }

    public void onResume(){
        boolean isGPSActivatedNow = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(mGPSIsActivated != isGPSActivatedNow)
            if(mOnGPSEnabledListener != null)
                mOnGPSEnabledListener.onGPSEnabled(isGPSActivatedNow);

        mGPSIsActivated = isGPSActivatedNow;
    }

}
