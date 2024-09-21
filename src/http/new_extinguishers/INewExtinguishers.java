package http.new_extinguishers;

import models.ExtinguisherModel;
import models.ProtokolModels;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface INewExtinguishers {

    @GET("/extinguisher_shop")
    Call<List<ExtinguisherModel>> getNewExtinguishers(@Header("Authorization") String accessToken);

    @POST("/insert_new_extinguisher")
    Call<String> insertNewExtinguisher(@Body  ProtokolModels body,@Header("Authorization") String accessToken);

    @POST("create_new_extingusihser")
    Call<Integer> createExtinguisher(@Body ExtinguisherModel body);


    @PUT("/update_extinguisher_quantity/{quantity}/{kontragent}/{invoiceByKontragent}/{type}/{weight}/{category}/{brand}")
    Call<Integer> editExtinguisherQuantity(@Path("quantity") String quantity, @Path("kontragent") String kontragent,
                                           @Path("invoiceByKontragent") String invoiceByKontragent, @Path("type") String type,
                                           @Path("weight") String weight, @Path("category") String category,
                                           @Path("brand") String brand);

    @PUT("/update_extinguisher_price/{price}/{percentProfit}/{type}/{weight}/{category}/{brand}/{client}/{invoice}")
    Call<Integer> editExtinguisherPrice(@Path("price") String price, @Path("percentProfit") String percentProfit,
                                        @Path("type") String type, @Path("weight") String weight,
                                        @Path("category") String category, @Path("brand") String brand,
                                        @Path("client") String client, @Path("invoice") String invoice);

    @DELETE("/delete_extinguisher/{type}/{weight}/{category}/{brand}/{invoiceByKontragent}/{kontragent}")
    Call<Integer> deleteExtinguisher(@Path("type") String type, @Path("weight") String weight,
                                     @Path("category") String category, @Path("brand") String brand,
                                     @Path("invoiceByKontragent") String invoiceByKontragent, @Path("kontragent") String kontragent);

}
