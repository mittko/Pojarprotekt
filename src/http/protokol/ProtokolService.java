package http.protokol;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.BrackModels;
import models.ProtokolModel;
import models.ProtokolModels;
import models.ServiceOrderModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.MainPanel;

import java.util.List;

import static utils.MainPanel.ACCESS_TOKEN;

public class ProtokolService extends ServiceAPI {

    public IProtokol getService() {
        return getRetrofit().create(IProtokol.class);
    }

    public void getServiceOrderByBarcode(String barcode, String serialNumber,RequestCallback2 callback) {
        getService().getServiceOrderByBarcode(barcode,serialNumber, ACCESS_TOKEN).enqueue(new Callback<ServiceOrderModel>() {
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

    public void insertProtokol(ProtokolModels body, RequestCallback2 callback) {
        getService().insertProtokol(body,ACCESS_TOKEN).enqueue(new Callback<String>() {
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

    public void insertBrack(BrackModels body, RequestCallback2 callback) {
        getService().insertBrack(body).enqueue(new Callback<String>() {
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

    public void getProtokolInfo(String number, RequestCallback callback) {
        getService().getProtokolInfo(number,ACCESS_TOKEN).enqueue(new Callback<List<ProtokolModel>>() {
            @Override
            public void onResponse(Call<List<ProtokolModel>> call, Response<List<ProtokolModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ProtokolModel>> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
}
