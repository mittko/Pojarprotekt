package http.technical_review;

import com.google.gson.Gson;
import exceptions.ErrorDialog;
import http.RequestCallback;
import http.base.ServiceAPI;
import models.TechnicalReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Collections;
import java.util.List;

public class TechnicalReviewService extends ServiceAPI {

    public TechnicalReviewService(){}
    private IGetTechnicalReview getService() {
        return getRetrofit().create(IGetTechnicalReview.class);
    }

    public void getTechnicalReview(String from, String to, RequestCallback callback) {
        getService().getTechnicalReview(from,to).enqueue(new Callback<List<TechnicalReview>>() {
            @Override
            public void onResponse(Call<List<TechnicalReview>> call, Response<List<TechnicalReview>> response) {

                if(response.isSuccessful()) {

                    callback.callback(response.body());

                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<TechnicalReview>> call, Throwable throwable) {
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

}
