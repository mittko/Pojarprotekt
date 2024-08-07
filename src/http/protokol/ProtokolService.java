package http.protokol;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.ProtokolModelBodyList;
import models.ServiceOrderModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;

public class ProtokolService extends ServiceAPI {

    public IProtokol getService() {
        return getRetrofit().create(IProtokol.class);
    }

    public void getServiceOrderByBarcode(String barcode, String serialNumber,RequestCallback2 callback) {
        getService().getServiceOrderByBarcode(barcode,serialNumber).enqueue(new Callback<ServiceOrderModel>() {
            @Override
            public void onResponse(Call<ServiceOrderModel> call, Response<ServiceOrderModel> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<ServiceOrderModel> call, Throwable throwable) {
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void insertProtokol(ProtokolModelBodyList body, RequestCallback2 callback) {
        getService().insertProtokol(body).enqueue(new Callback<String>() {
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
