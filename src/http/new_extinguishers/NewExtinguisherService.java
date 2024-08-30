package http.new_extinguishers;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.ExtinguisherModel;
import models.ProtokolModels;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class NewExtinguisherService extends ServiceAPI {

    public INewExtinguishers getService() {
        return getRetrofit().create(INewExtinguishers.class);
    }
    public void getExtinguishers(RequestCallback2 callback) {
        getService().getNewExtinguishers().enqueue(new Callback<List<ExtinguisherModel>>() {
            @Override
            public void onResponse(Call<List<ExtinguisherModel>> call, Response<List<ExtinguisherModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ExtinguisherModel>> call, Throwable throwable) {
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void insertNewExtinguisher(ProtokolModels body, RequestCallback2 callback) {
        getService().insertNewExtinguisher(body).enqueue(new Callback<String>() {
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
