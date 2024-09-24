package http.protokol;

import models.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IProtokol {

    @GET("/service_order_by_barcode")
    Call<ServiceOrderModel> getServiceOrderByBarcode(@Query("barcode") String barcode, @Query("serial_number") String serialNumber
    , @Header("Authorization") String accessToken);

    @POST("/insert_protokol")
    Call<String> insertProtokol(@Body ProtokolModels body, @Header("Authorization") String accessToken);

    @POST("/insert_brack")
    Call<String> insertBrack(@Body BrackModels body);

    @GET("/protokol_info")
    Call<ProtokolInfo> getProtokolInfo(@Query("number") String number, @Header("Authorization") String accessToken);
}
