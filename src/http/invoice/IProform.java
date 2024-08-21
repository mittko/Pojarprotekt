package http.invoice;

import http.base.ServiceAPI;
import models.InvoiceModels;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IProform {

    @GET("/invoice_info")
    Call<InvoiceModels> getProformInfo(@Query("id") String id);
}
