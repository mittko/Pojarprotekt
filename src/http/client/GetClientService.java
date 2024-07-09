package http.client;

import http.RequestCallback2;
import http.base.ServiceAPI;
import models.Firm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetClientService extends ServiceAPI {

    public IGetClient getService() {
        return getRetrofit().create(IGetClient.class);
    }

    public void getFirm(String client, RequestCallback2 requestCallback2) {
        getService().getFirm(client).enqueue(new Callback<Firm>() {
            @Override
            public void onResponse(Call<Firm> call, Response<Firm> response) {
                requestCallback2.callback(response.body());
            }

            @Override
            public void onFailure(Call<Firm> call, Throwable throwable) {

            }
        });
    }
}
