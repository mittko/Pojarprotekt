package http.service_order;

import http.base.ServiceAPI;
import models.ServiceOrderBodyList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IServiceOrder {

    @POST("/insert_service_order")
    Call<Integer> insertServiceOrder(@Body ServiceOrderBodyList body);
}
