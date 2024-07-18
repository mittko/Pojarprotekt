package http.reports;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.base.ServiceAPI;
import models.ServiceOrderReports;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;

public class GetReportsService extends ServiceAPI {

    private IGetReports getService() {
        return getRetrofit().create(IGetReports.class);
    }

    public void getServiceOrders(HashMap<String, String> optionsMap, RequestCallback callback) {
        getService().getServiceOrders(optionsMap).enqueue(new Callback<List<ServiceOrderReports>>() {
            @Override
            public void onResponse(Call<List<ServiceOrderReports>> call, Response<List<ServiceOrderReports>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ServiceOrderReports>> call, Throwable throwable) {
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
}
