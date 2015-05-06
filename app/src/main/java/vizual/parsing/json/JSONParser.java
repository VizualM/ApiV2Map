package vizual.parsing.json;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import vizual.dal.Faction;
import vizual.dal.GeoPoint;
import vizual.dal.Structure;
import vizual.dal.Tile;

/**
 * Created by root on 06/05/15.
 */
public class JSONParser {
    /**
     * @param in : buffer with the php result
     * @param bufSize : size of the buffer
     * @return : the string corresponding to the buffer
     */
    public static String InputStreamToString (InputStream in, int bufSize) {
        final StringBuilder out = new StringBuilder();
        final byte[] buffer = new byte[bufSize];
        try {
            for (int ctr; (ctr = in.read(buffer)) != -1;) {
                out.append(new String(buffer, 0, ctr));
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot convert stream to string", e);
        }
        // On retourne la chaine contenant les donnees de l'InputStream
        return out.toString();
    }

    /**
     * @param in : buffer with the php result
     * @return : the string corresponding to the buffer
     */
    public static String InputStreamToString (InputStream in) {
        // On appelle la methode precedente avec une taille de buffer par defaut
        return InputStreamToString(in, 1024);
    }

    public static ArrayList<Tile> getEvents(String topLon, String topLat, String botLon, String botLat) {
        try {
            String myurl = "http://127.0.0.1/Eukaryote/web/app_dev.php/tiles?topLon="+topLon+"&topLat="+topLat+"&botLon="+botLon+"botLat="+botLat;
            Log.e("URL", myurl);
            ArrayList<Tile> TileArray = new ArrayList<Tile>();
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            String result = JSONParser.InputStreamToString(inputStream);


            try {
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    String id = obj.get("id").toString();
                    JSONArray botL = new JSONArray(obj.get("bottom_left").toString());
                    float lat = (float) botL.getDouble(1);
                    float lon = (float) botL.getDouble(0);
                    GeoPoint bottomLeft = new GeoPoint(lat, lon);
                    JSONArray botR = new JSONArray(obj.get("bottom_right").toString());
                    lat = (float) botR.getDouble(1);
                    lon = (float) botR.getDouble(0);
                    GeoPoint bottomRight = new GeoPoint(lat, lon);
                    JSONArray topL = new JSONArray(obj.get("top_left").toString());
                    lat = (float) topL.getDouble(1);
                    lon = (float) topL.getDouble(0);
                    GeoPoint topLeft = new GeoPoint(lat, lon);
                    JSONArray topR = new JSONArray(obj.get("top_right").toString());
                    lat = (float) topR.getDouble(1);
                    lon = (float) topR.getDouble(0);
                    GeoPoint topRight = new GeoPoint(lat, lon);
                    String faction = obj.get("faction").toString();
                    Faction faction1 = new Faction(faction);
                    String structure = obj.get("structure").toString();
                    Structure structure1 = new Structure(structure);

                    TileArray.add(new Tile(id, faction1, structure1, bottomLeft, bottomRight, topLeft, topRight));
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return TileArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
