package http.invoice;

import http.base.ServiceAPI;
import models.AcquittanceModels;
import models.InvoiceModels;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.*;

public interface IProform {

    @GET("/invoice_info")
    Call<InvoiceModels> getProformInfo(@Query("id") String id,@Header("Authorization") String accessToken);

    @POST("/insert_invoice")
    Call<String> insertInvoice(@Body InvoiceModels body);

    @POST("/insert_proform")
    Call<String> insertProform(@Body InvoiceModels body);

    @POST("/insert_acquittance/{artikulTable}")
    Call<String> insertAcquittance(@Body AcquittanceModels body, @Path("artikulTable") String artikulTable);
}
