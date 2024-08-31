package http.sklad;

import models.ArtikulModel;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IGetArtikuls {

    @GET("/artikuls_data")
    Call<List<ArtikulModel>> getArtikuls(@Query("grey") boolean grey, @Query("order_by_date") boolean order_by_date);

    @POST("/insert_artikul")
    Call<Integer> insertArtikul(@Body ArtikulModel body);

    @DELETE("/delete_artikul/{artikul}/{kontragent}/{invoiceByKontragent}")
    Call<Integer> deleteArtikul(@Path("artikul") String artikul, @Path("kontragent") String kontragent, @Path("invoiceByKontragent") String invoiceByKontragent);
}
