package http.service_order;

import exceptions.ErrorDialog;
import http.RequestCallback2;
import http.base.ServiceAPI;
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
}
