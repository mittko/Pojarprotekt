package http.reports;

import clients.editclient.IncorrectPerson;
import exceptions.ErrorDialog;
import http.RequestCallback;
import http.base.ServiceAPI;
import models.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

import static utils.MainPanel.ACCESS_TOKEN;

public class GetReportsService extends ServiceAPI {

    private IGetReports getService() {
        return getRetrofit().create(IGetReports.class);
    }

    public void getServiceOrders(HashMap<String, String> optionsMap, RequestCallback callback) {
        getService().getServiceOrders(optionsMap,ACCESS_TOKEN).enqueue(new Callback<List<ServiceOrderModel>>() {
            @Override
            public void onResponse(Call<List<ServiceOrderModel>> call, Response<List<ServiceOrderModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(null);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ServiceOrderModel>> call, Throwable throwable) {
                 callback.callback(null);
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getProtokols(HashMap<String, String> optionsMap, RequestCallback callback) {
        getService().getProtokols(optionsMap,ACCESS_TOKEN).enqueue(new Callback<List<ProtokolModel>>() {
            @Override
            public void onResponse(Call<List<ProtokolModel>> call, Response<List<ProtokolModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(null);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ProtokolModel>> call, Throwable throwable) {
                    callback.callback(null);
                    ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getBrack(HashMap<String, String> optionsMap, RequestCallback callback) {
        getService().getBrack(optionsMap,ACCESS_TOKEN).enqueue(new Callback<List<BrackModel>>() {
            @Override
            public void onResponse(Call<List<BrackModel>> call, Response<List<BrackModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(null);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<BrackModel>> call, Throwable throwable) {
                callback.callback(null);
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getInvoices(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getInvoices(optionsParam).enqueue(new Callback<List<InvoiceModel>>() {
            @Override
            public void onResponse(Call<List<InvoiceModel>> call, Response<List<InvoiceModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<InvoiceModel>> call, Throwable throwable) {
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getProforms(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getProforms(optionsParam).enqueue(new Callback<List<InvoiceModel>>() {
            @Override
            public void onResponse(Call<List<InvoiceModel>> call, Response<List<InvoiceModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<InvoiceModel>> call, Throwable throwable) {
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getAcquittance(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getAcquittance(optionsParam).enqueue(new Callback<List<AcquittanceModel>>() {
            @Override
            public void onResponse(Call<List<AcquittanceModel>> call, Response<List<AcquittanceModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<AcquittanceModel>> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getArtikulsNames(RequestCallback callback) {
        getService().getArtikuls(ACCESS_TOKEN).enqueue(new Callback<List<ArtikulsReports>>() {
            @Override
            public void onResponse(Call<List<ArtikulsReports>> call, Response<List<ArtikulsReports>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ArtikulsReports>> call, Throwable throwable) {
                  ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getNewExtinguishers(RequestCallback callback) {
        getService().getNewExtinguishers(ACCESS_TOKEN).enqueue(new Callback<List<NewExtinguishersReports>>() {
            @Override
            public void onResponse(Call<List<NewExtinguishersReports>> call, Response<List<NewExtinguishersReports>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<NewExtinguishersReports>> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getClients(RequestCallback callback) {
        getService().getClients(ACCESS_TOKEN).enqueue(new Callback<List<IncorrectPerson>>() {
            @Override
            public void onResponse(Call<List<IncorrectPerson>> call, Response<List<IncorrectPerson>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<IncorrectPerson>> call, Throwable throwable) {
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getDeliveries(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getDeliveries(optionsParam).enqueue(new Callback<List<DeliveryReports>>() {
            @Override
            public void onResponse(Call<List<DeliveryReports>> call, Response<List<DeliveryReports>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryReports>> call, Throwable throwable) {
                  ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getDeliveryDataForSale(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getDeliveryDataForSale(optionsParam).enqueue(new Callback<List<DeliveryReports>>() {
            @Override
            public void onResponse(Call<List<DeliveryReports>> call, Response<List<DeliveryReports>> response) {
                 if(response.isSuccessful()) {
                     callback.callback(response.body());
                 } else {
                     ErrorDialog.showHttpError(response);
                 }
            }

            @Override
            public void onFailure(Call<List<DeliveryReports>> call, Throwable throwable) {
                    ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getInvoiceDataForSale(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getInvoiceDataForSale(optionsParam).enqueue(new Callback<List<InvoiceModel>>() {
            @Override
            public void onResponse(Call<List<InvoiceModel>> call, Response<List<InvoiceModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<InvoiceModel>> call, Throwable throwable) {
                  ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getDeliveryDataForAvailability(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getDeliveryDataForAvailability(optionsParam).enqueue(new Callback<List<DeliveryReports>>() {
            @Override
            public void onResponse(Call<List<DeliveryReports>> call, Response<List<DeliveryReports>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryReports>> call, Throwable throwable) {
                  ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getInvoiceDataForAvailability(HashMap<String, String > optionsParam, RequestCallback callback) {
        getService().getInvoiceDataForAvailability(optionsParam).enqueue(new Callback<List<InvoiceModel>>() {
            @Override
            public void onResponse(Call<List<InvoiceModel>> call, Response<List<InvoiceModel>> response) {
                       if(response.isSuccessful()) {
                           callback.callback(response.body());
                       } else {
                           ErrorDialog.showHttpError(response);
                       }
            }

            @Override
            public void onFailure(Call<List<InvoiceModel>> call, Throwable throwable) {
                          ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getCreditNotes(HashMap<String, String> optionsParam, RequestCallback callback) {
        getService().getCreditNotes(optionsParam).enqueue(new Callback<List<CreditNoteReports>>() {
            @Override
            public void onResponse(Call<List<CreditNoteReports>> call, Response<List<CreditNoteReports>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<CreditNoteReports>> call, Throwable throwable) {
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void createCreditNote(CreditNoteBodyList creditNoteBodyList) {
        getService().createCreditNote(creditNoteBodyList).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    JOptionPane.showMessageDialog(null,"Кредитното известие е записано успешно");
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
}
