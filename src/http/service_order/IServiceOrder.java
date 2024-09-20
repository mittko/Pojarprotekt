package http.service_order;

import models.ProtokolModel;
import models.ServiceOrderModels;
import retrofit2.Call;
import retrofit2.http.*;

public interface IServiceOrder {

    @POST("/insert_service_order")
    Call<Integer> insertServiceOrder(@Body ServiceOrderModels body, @Header("Authorization") String accessToken);

    @GET("/protokol_info_barcode")
    Call<ProtokolModel> getProtokolInfoByBarcode(@Query("barcode") String barcode, @Query("serial_number")String serialNumber
            ,@Header("Authorization") String accessToken);


    @GET("/next_serial_number")
    Call<String> getNextSerialNumber(@Header("Authorization") String accessToken);

}
