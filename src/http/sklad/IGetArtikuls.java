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

    @PUT("/rename_artikul/{oldName}/{newName}")
    Call<Integer> renameArtikul(@Path("oldName") String oldName, @Path("newName") String newName);


    @PUT("/edit_artikul_quantity/{artikul}/{kontragent}/{invoiceByKontragent}/{newQuantity}")
    Call<Integer> editArtikulQuantity(@Path("artikul") String artikul, @Path("kontragent") String kontragent,
                                      @Path("invoiceByKontragent") String invoiceByKontragent,
                                      @Path("newQuantity") String newQuantity);

    @PUT("/edit_artikul_price/{newValue}/{percentProfit}/{artikul}/{kontragent}/{invoiceByKontragent}")
    Call<Integer> editArtikulPrice(@Path("newValue") String newValue, @Path("percentProfit") String percentProfit, @Path("artikul") String artikul,
                                   @Path("kontragent") String kontragent, @Path("invoiceByKontragent") String invoiceByKontragent);
}
