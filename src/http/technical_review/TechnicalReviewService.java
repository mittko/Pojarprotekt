package http.technical_review;

import com.google.gson.Gson;
import http.base.ServiceAPI;
import models.TechnicalReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class TechnicalReviewService extends ServiceAPI {

    public TechnicalReviewService(){}
    private IGetTechnicalReview getService() {
        return getRetrofit().create(IGetTechnicalReview.class);
    }

    public void getTechnicalReview(String from, String to) {
        getService().getTechnicalReview(from,to).enqueue(new Callback<List<TechnicalReview>>() {
            @Override
            public void onResponse(Call<List<TechnicalReview>> call, Response<List<TechnicalReview>> response) {
                System.out.println(response.code());
                if(response.isSuccessful()) {
                    List<TechnicalReview> technicalReviewList = response.body();
                    if(technicalReviewList != null) {
                        for(TechnicalReview technicalReview : technicalReviewList) {
                            System.out.println(technicalReview);
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<TechnicalReview>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

}
