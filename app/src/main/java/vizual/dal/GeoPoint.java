package vizual.dal;

/**
 * Created by swater on 12/02/2015.
 */
public class GeoPoint {
    private float Lat;
    private float Long;

    public float getLat() {
        return Lat;
    }

    public void setLat(float lat) {
        Lat = lat;
    }

    public float getLong() {
        return Long;
    }

    public void setLong(float aLong) {
        Long = aLong;
    }

    public GeoPoint(float _lat, float _lon){
        this.Lat = _lat;
        this.Long = _lon;
    }

    public GeoPoint(){
    }
}
