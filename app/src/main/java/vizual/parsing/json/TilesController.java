package vizual.parsing.json;

import android.os.AsyncTask;

import java.util.ArrayList;


import vizual.dal.Tile;

/**
 * Created by yun on 01/04/2015.
 */
public class TilesController {

        public ArrayList<Tile> Parse (String topLon, String topLat, String botLon, String botLat){
            myAsyncSyncClass myTask = new myAsyncSyncClass();
            try {
                return myTask.execute(topLon, topLat, botLon, botLat).get();
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        private class myAsyncSyncClass extends AsyncTask<String, Void, ArrayList<Tile>> {

            @Override
            protected ArrayList<Tile> doInBackground(String... Strings) {
                JSONParser parser = new JSONParser();
                return parser.getEvents(Strings[0], Strings[1], Strings[2], Strings[3]);
            }
        }


      /*  private static String topLon="-0.66993999999999";
        private static String topLat="44.70081";
        private static String botLon="-0.72636";
        private static String botLat="44.70009";*/
        //private static String url = "http://"+ipAdress+"/Eukaryote/web/app_dev.php/tiles?topLon="+topLon+"&topLat="+topLat+"&botLon="+botLon+"botLat="+botLat;


}
