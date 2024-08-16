package http.sklad;

import models.ArtikulModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IGetArtikuls {

    @GET("/artikuls_data")
    Call<List<ArtikulModel>> getArtikuls(@Query("grey") boolean grey, @Query("order_by_date") boolean order_by_date);
}
