package http.protokol;

import models.ProtokolModelBodyList;
import models.ServiceOrderModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IProtokol {

    @GET("/service_order_by_barcode")
    Call<ServiceOrderModel> getServiceOrderByBarcode(@Query("barcode") String barcode, @Query("serial_number") String serialNumber);

    @POST("/insert_protokol")
    Call<String> insertProtokol(@Body ProtokolModelBodyList body);
}
