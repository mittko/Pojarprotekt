package http.reports;

import clients.editclient.IncorrectPerson;
import models.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface IGetReports {

    @GET("/service_orders")
    Call<List<ServiceOrderModel>> getServiceOrders(@QueryMap HashMap<String, String> optionsParams);

    @GET("/protokols")
    Call<List<ProtokolModel>> getProtokols(@QueryMap HashMap<String,String> optionsParam);

    @GET("/brack")
    Call<List<BrackModel>> getBrack(@QueryMap HashMap<String, String> optionsParam);

    @GET("/invoices")
    Call<List<InvoiceModel>> getInvoices(@QueryMap HashMap<String, String> optionsParam);

    @GET("/proforms")
    Call<List<InvoiceModel>> getProforms(@QueryMap HashMap<String, String> optionsParam);

    @GET("/acquittance")
    Call<List<AcquittanceModel>> getAcquittance(@QueryMap HashMap<String, String> optionParam);

    @GET("/artikuls")
    Call<List<ArtikulsReports>> getArtikuls();

    @GET("/new_extinguishers")
    Call<List<NewExtinguishersReports>> getNewExtinguishers();

    @GET("/clients")
    Call<List<IncorrectPerson>> getClients();

    @GET("/deliveries")
    Call<List<DeliveryReports>> getDeliveries(@QueryMap HashMap<String, String> optionsParam);

    @GET("/delivery_data_for_sales")
    Call<List<DeliveryReports>> getDeliveryDataForSale(@QueryMap HashMap<String, String> optionsParam);

    @GET("/invoice_data_for_sales")
    Call<List<InvoiceModel>> getInvoiceDataForSale(@QueryMap HashMap<String, String> optionsParam);

    @GET("/delivery_data_for_availability")
    Call<List<DeliveryReports>> getDeliveryDataForAvailability(@QueryMap HashMap<String, String> optionsParam);

    @GET("/invoice_data_for_availability")
    Call<List<InvoiceModel>> getInvoiceDataForAvailability(@QueryMap HashMap<String, String> optionsParam);

    @GET("/credit_notes")
    Call<List<CreditNoteReports>> getCreditNotes(@QueryMap HashMap<String,String> optionsParam);

    @POST("/insert_credit_note")
    Call<String> createCreditNote(@Body CreditNoteBodyList creditNoteBodyList);

}

