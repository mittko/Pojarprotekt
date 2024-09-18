package http.reports;

import clients.editclient.IncorrectPerson;
import models.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface IGetReports {

    @GET("/service_orders")
    Call<List<ServiceOrderModel>> getServiceOrders(@QueryMap HashMap<String, String> optionsParams,
                                                   @Header("Authorization") String accessToken);

    @GET("/protokols")
    Call<List<ProtokolModel>> getProtokols(@QueryMap HashMap<String,String> optionsParam,
                                           @Header("Authorization") String accessToken);

    @GET("/brack")
    Call<List<BrackModel>> getBrack(@QueryMap HashMap<String, String> optionsParam,
                                    @Header("Authorization") String accessToken);

    @GET("/invoices")
    Call<List<InvoiceModel>> getInvoices(@QueryMap HashMap<String, String> optionsParam,
                                         @Header("Authorization") String accessToken);

    @GET("/proforms")
    Call<List<InvoiceModel>> getProforms(@QueryMap HashMap<String, String> optionsParam,
                                         @Header("Authorization") String accessToken);

    @GET("/acquittance")
    Call<List<AcquittanceModel>> getAcquittance(@QueryMap HashMap<String, String> optionParam,
                                                @Header("Authorization") String accessToken);

    @GET("/artikuls")
    Call<List<ArtikulsReports>> getArtikuls(@Header("Authorization") String accessToken);

    @GET("/new_extinguishers")
    Call<List<NewExtinguishersReports>> getNewExtinguishers(@Header("Authorization") String accessToken);

    @GET("/clients")
    Call<List<IncorrectPerson>> getClients(@Header("Authorization") String accessToken);

    @GET("/deliveries")
    Call<List<DeliveryReports>> getDeliveries(@QueryMap HashMap<String, String> optionsParam,
                                              @Header("Authorization") String accessToken);

    @GET("/delivery_data_for_sales")
    Call<List<DeliveryReports>> getDeliveryDataForSale(@QueryMap HashMap<String, String> optionsParam,
                                                       @Header("Authorization") String accessToken);

    @GET("/invoice_data_for_sales")
    Call<List<InvoiceModel>> getInvoiceDataForSale(@QueryMap HashMap<String, String> optionsParam,
                                                   @Header("Authorization") String accessToken);

    @GET("/delivery_data_for_availability")
    Call<List<DeliveryReports>> getDeliveryDataForAvailability(@QueryMap HashMap<String, String> optionsParam,
                                                               @Header("Authorization") String accessToken);

    @GET("/invoice_data_for_availability")
    Call<List<InvoiceModel>> getInvoiceDataForAvailability(@QueryMap HashMap<String, String> optionsParam,
                                                           @Header("Authorization") String accessToken);

    @GET("/credit_notes")
    Call<List<CreditNoteReports>> getCreditNotes(@QueryMap HashMap<String,String> optionsParam,
                                                 @Header("Authorization") String accessToken);

    @POST("/insert_credit_note")
    Call<String> createCreditNote(@Body CreditNoteBodyList creditNoteBodyList,@Header("Authorization") String accessToken);

}

