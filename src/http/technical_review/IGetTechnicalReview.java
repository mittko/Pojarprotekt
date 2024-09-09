package http.technical_review;

import models.TechnicalReview;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import java.util.List;

public interface IGetTechnicalReview {

    @GET("/tech_review")
    Call<List<TechnicalReview>> getTechnicalReview(@Query("from") String from, @Query("to") String to,
                                                   @Header("Authorization") String accessToken);
}
