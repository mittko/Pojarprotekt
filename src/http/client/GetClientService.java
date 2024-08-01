package http.client;

import exceptions.ErrorDialog;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.Firm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;

public class GetClientService extends ServiceAPI {

    public IGetClient getService() {
        return getRetrofit().create(IGetClient.class);
    }

    public void getFirm(String client, RequestCallback2 requestCallback2) {
        getService().getFirm(client).enqueue(new Callback<Firm>() {
            @Override
            public void onResponse(Call<Firm> call, Response<Firm> response) {
                if(response.isSuccessful()) {
                    requestCallback2.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Firm> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void insertClient(Firm body, RequestCallback2 callback) {
        getService().insertClient(body).enqueue(new Callback<Integer>() {
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

    public void insertFirm(Firm firm, RequestCallback2 callback) {
        getService().insertFirm(firm).enqueue(new Callback<Integer>() {
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

    public void deleteClient(String name , RequestCallback2 callback) {
        getService().deleteClient(name).enqueue(new Callback<Integer>() {
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

    public void editFirm(Firm firm, String oldName, RequestCallback2 callback) {
        getService().editFirm(firm,oldName).enqueue(new Callback<Integer>() {
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

    public void editClient(Firm firm, String oldName, RequestCallback2 callback) {
        getService().editClient(firm,oldName).enqueue(new Callback<Integer>() {
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
}
