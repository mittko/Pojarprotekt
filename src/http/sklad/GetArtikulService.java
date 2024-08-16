package http.sklad;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.base.ServiceAPI;
import models.ArtikulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class GetArtikulService extends ServiceAPI {

    public IGetArtikuls getService() {
        return getRetrofit().create(IGetArtikuls.class);
    }

    public void getArtikuls(boolean grey, boolean orderByDate, RequestCallback callback) {
        getService().getArtikuls(grey, orderByDate).enqueue(new Callback<List<ArtikulModel>>() {
            @Override
            public void onResponse(Call<List<ArtikulModel>> call, Response<List<ArtikulModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ArtikulModel>> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
}
