package http.service_order;

import exceptions.ErrorDialog;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.ProtokolModel;
import models.ServiceOrderModels;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static utils.MainPanel.ACCESS_TOKEN;

public class ServiceOrderService extends ServiceAPI {

    public IServiceOrder getService() {
        return getRetrofit().create(IServiceOrder.class);
    }

    public void insertServiceOrder(ServiceOrderModels body, RequestCallback2 callback) {
        getService().insertServiceOrder(body,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
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
        getService().getProtokolInfoByBarcode(barcode,serialNumber,ACCESS_TOKEN).enqueue(new Callback<ProtokolModel>() {
            @Override
            public void onResponse(Call<ProtokolModel> call, Response<ProtokolModel> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(null);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<ProtokolModel> call, Throwable throwable) {
                    callback.callback(null);
                    ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getNextSerialNumber(RequestCallback2 callback) {
        getService().getNextSerialNumber(ACCESS_TOKEN).enqueue(new Callback<String>() {
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
