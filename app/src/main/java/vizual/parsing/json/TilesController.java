package vizual.parsing.json;

import android.os.Environment;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vizual.dal.Tile;

/**
 * Created by yun on 01/04/2015.
 */
public class TilesController {

        public ArrayList<Tile> Parse (String myRequest, String topLon, String topLat, String botLon, String botLat){
            JSONParser parser = new JSONParser();
            ArrayList<Tile> myTiles = parser.getEvents(topLon, topLat, botLon, botLat);
            return myTiles;
        }


      /*  private static String topLon="-0.66993999999999";
        private static String topLat="44.70081";
        private static String botLon="-0.72636";
        private static String botLat="44.70009";*/
        //private static String url = "http://"+ipAdress+"/Eukaryote/web/app_dev.php/tiles?topLon="+topLon+"&topLat="+topLat+"&botLon="+botLon+"botLat="+botLat;


}
