package http.new_extinguishers;

import models.ExtinguisherModel;
import models.ProtokolModels;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface INewExtinguishers {

    @GET("/extinguisher_shop")
    Call<List<ExtinguisherModel>> getNewExtinguishers();

    @POST("/insert_new_extinguisher")
    Call<String> insertNewExtinguisher(@Body  ProtokolModels body);
}
