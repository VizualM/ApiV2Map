package vizual.parsing.json;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by charl_000 on 18/03/2015.
 */
public class Parser {

    public ArrayList<Tuile> Parse(double topleft, double topright,
                                  double bottomleft, double bottomright, String queryurl){

        final ArrayList<Tuile> myResult = new ArrayList<Tuile>();
        EucaryoteService eucaryoteService;

        RestAdapter serviceForumBuilder = new RestAdapter.Builder().setEndpoint("queryurl").build();
        eucaryoteService = serviceForumBuilder.create(EucaryoteService.class);
        eucaryoteService.getCategories("1002", new Callback<List<Tuile>>() {
            @Override
            public void success(List<Tuile> t, Response response) {
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
