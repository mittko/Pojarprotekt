package http.invoice;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.AcquittanceModels;
import models.InvoiceModels;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static utils.MainPanel.ACCESS_TOKEN;

public class ProformService extends ServiceAPI {

    public IProform getService() {
        return getRetrofit().create(IProform.class);
    }

    public void getProformInfo(String id, RequestCallback2 callback) {
        getService().getProformInfo(id,ACCESS_TOKEN).enqueue(new Callback<InvoiceModels>() {
            @Override
            public void onResponse(Call<InvoiceModels> call, Response<InvoiceModels> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<InvoiceModels> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void insertInvoice(InvoiceModels models, RequestCallback2 callback) {
        getService().insertInvoice(models,ACCESS_TOKEN).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
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

    public void insertProform(InvoiceModels models, RequestCallback2 callback) {
        getService().insertProform(models,ACCESS_TOKEN).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
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

    public void insertAcquittance(AcquittanceModels body, String artikulTable, RequestCallback2 callback) {
        getService().insertAcquittance(body, artikulTable,ACCESS_TOKEN).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
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
