package vizual.parsing.json;

import android.content.pm.LauncherApps;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by charl_000 on 04/03/2015.
 */
public interface EucaryoteService {

    @GET("/tile")
    void getCategories(@Query("id") String id, Callback<List<Tuile>> callback);


}
