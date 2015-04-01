package vizual.parsing.json;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vizual.dal.Tile;
import vizual.dal.GeoPoint;

/**
 * Created by charl_000 on 18/03/2015.
 */
public class Parser {

    public ArrayList<Tile> Parse(GeoPoint topleft, GeoPoint topright,
                                 GeoPoint bottomleft, GeoPoint bottomright, String queryurl){

        final ArrayList<Tile> myResult = new ArrayList<Tile>();
        EucaryoteService eucaryoteService;

        RestAdapter serviceForumBuilder = new RestAdapter.Builder().setEndpoint("queryurl").build();
        eucaryoteService = serviceForumBuilder.create(EucaryoteService.class);
        eucaryoteService.getCategories("1002", new Callback<List<Tile>>() {
            @Override
            public void success(List<Tile> t, Response response) {
                for (int i=0;i < t.size();i++){
                    myResult.set(i,t.get(i));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.getResponse().getStatus();
            }
        });

        return myResult;
    }
}
