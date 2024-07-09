package http.client;

import models.Firm;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetClient {

    @GET("/client_data")
    Call<Firm> getFirm(@Query("client") String client);
}
