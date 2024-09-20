package http.client;

import models.Firm;
import retrofit2.Call;
import retrofit2.http.*;



public interface IGetClient {

    @GET("/client_data")
    Call<Firm> getFirm(@Query("client") String client, @Header("Authorization") String accessToken);

    @POST("/insert_client")
    Call<Integer> insertClient(@Body Firm body, @Header("Authorization") String accessToken);

    @POST("/insert_firm")
    Call<Integer> insertFirm(@Body Firm firm, @Header("Authorization") String accessToken);

    @DELETE("/delete_client/{name}")
    Call<Integer> deleteClient(@Path("name") String name, @Header("Authorization") String accessToken);

    @PUT("/edit_firm/{old_name}")
    Call<Integer> editFirm(@Body Firm firm, @Path("old_name") String oldName, @Header("Authorization") String accessToken);

    @PUT("/edit_client/{old_name}")
    Call<Integer> editClient(@Body Firm firm, @Path("old_name") String oldName, @Header("Authorization") String accessToken);
}
