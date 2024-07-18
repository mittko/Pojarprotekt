package http.reports;

import models.ServiceOrderReports;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.HashMap;
import java.util.List;

public interface IGetReports {

    @GET("/service_orders")
    Call<List<ServiceOrderReports>> getServiceOrders(@QueryMap HashMap<String, String> optionsParams);

}

