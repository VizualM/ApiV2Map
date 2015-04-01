package vizual.parsing.json;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import vizual.dal.Tile;
import vizual.dal.GeoPoint;

/**
 * Created by yun on 01/04/2015.
 */
public class TilesController {

        public void Parse (String myRequest){}
        private static final String DL_URL = "http://127.0.0.1/Eukaryote/web/app_dev.php/tiles?topLon=-0.66993999999999&topLat=44.70081&botLon=-0.72636&botLat=44.70009";

        private ObjectMapper objectMapper = null;
        private JsonFactory jsonFactory = null;
        private ArrayList<Tile> tileList = null;
        private File jsonFile;

        public TilesController() {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonFactory jsonFactory = new JsonFactory();
        }

        public void init() {
            downloadJsonFile();
            try {
                JsonParser jp = jsonFactory.createJsonParser(jsonFile);
                Tiles tiles = objectMapper.readValue(jp, Tiles.class);
                tileList = tiles.get("");
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void downloadJsonFile() {
            try {
                createFileAndDirectory();
                URL url = new URL(TilesController.DL_URL);
                HttpURLConnection urlConnection;
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                FileOutputStream fileOutput = new FileOutputStream(jsonFile);
                InputStream inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void createFileAndDirectory() throws FileNotFoundException {
            final String extStorageDirectory = Environment
                    .getExternalStorageDirectory().toString();
            final String EukaryoteDirectory_path = extStorageDirectory + "/Eukaryote-android";
            File jsonOutputFile = new File(EukaryoteDirectory_path, "/");
            if (!jsonOutputFile.exists())
                jsonOutputFile.mkdirs();
            jsonFile = new File(jsonOutputFile, "tiles.json");
        }

        public ArrayList<Tile> findAll() {
            return tileList;
        }

        public Tile findById(int id) {
            return tileList.get(id);
        }

    }
