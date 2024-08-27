package http.invoice;

import http.base.ServiceAPI;
import models.AcquittanceModels;
import models.InvoiceModels;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IProform {

    @GET("/invoice_info")
    Call<InvoiceModels> getProformInfo(@Query("id") String id);

    @POST("/insert_invoice")
    Call<String> insertInvoice(@Body InvoiceModels body);

    @POST("/insert_proform")
    Call<String> insertProform(@Body InvoiceModels body);

    @POST("/insert_acquittance")
    Call<String> insertAcquittance(@Body AcquittanceModels body);
}
