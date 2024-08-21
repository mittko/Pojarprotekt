package http.protokol;

import models.BrackModels;
import models.ProtokolModel;
import models.ProtokolModels;
import models.ServiceOrderModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface IProtokol {

    @GET("/service_order_by_barcode")
    Call<ServiceOrderModel> getServiceOrderByBarcode(@Query("barcode") String barcode, @Query("serial_number") String serialNumber);

    @POST("/insert_protokol")
    Call<String> insertProtokol(@Body ProtokolModels body);

    @POST("/insert_brack")
    Call<String> insertBrack(@Body BrackModels body);

    @GET("/protokol_info")
    Call<List<ProtokolModel>> getProtokolInfo(@Query("number") String number);
}
