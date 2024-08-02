package http.service_order;

import models.ProtokolModels;
import models.ServiceOrderBodyList;
import retrofit2.Call;
import retrofit2.http.*;

public interface IServiceOrder {

    @POST("/insert_service_order")
    Call<Integer> insertServiceOrder(@Body ServiceOrderBodyList body);

    @GET("/protokol_info_barcode")
    Call<ProtokolModels> getProtokolInfoByBarcode(@Query("barcode") String barcode, @Query("serial_number") String serialNumber);


    @GET("/next_serial_number")
    Call<String> getNextSerialNumber();

}
