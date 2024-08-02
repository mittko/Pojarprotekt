package http.service_order;

import http.base.ServiceAPI;
import models.ProtokolModels;
import models.ServiceOrderBodyList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IServiceOrder {

    @POST("/insert_service_order")
    Call<Integer> insertServiceOrder(@Body ServiceOrderBodyList body);

    @GET("/protokol_info_barcode")
    Call<ProtokolModels> getProtokolInfoByBarcode(@Query("barcode") String barcode, @Query("serial_number") String serialNumber);


}
