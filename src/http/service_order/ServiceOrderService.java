package http.service_order;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.ProtokolModels;
import models.ServiceOrderBodyList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceOrderService extends ServiceAPI {

    public IServiceOrder getService() {
        return getRetrofit().create(IServiceOrder.class);
    }

    public void insertServiceOrder(ServiceOrderBodyList body, RequestCallback2 callback) {
        getService().insertServiceOrder(body).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(0);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                  callback.callback(0);
                  ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getProtokolInfoByBarcode(String barcode, String serialNumber,RequestCallback2 callback) {
        getService().getProtokolInfoByBarcode(barcode,serialNumber).enqueue(new Callback<ProtokolModels>() {
            @Override
            public void onResponse(Call<ProtokolModels> call, Response<ProtokolModels> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(null);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<ProtokolModels> call, Throwable throwable) {
                    callback.callback(null);
                    ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getNextSerialNumber(RequestCallback2 callback) {
        getService().getNextSerialNumber().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {
                        callback.callback(response.body());
                    } else {
                        callback.callback("");
                        ErrorDialog.showHttpError(response);
                    }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                    callback.callback("");
                    ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
}
