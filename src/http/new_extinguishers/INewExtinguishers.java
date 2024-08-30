package http.new_extinguishers;

import models.ExtinguisherModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface INewExtinguishers {

    @GET("/extinguisher_shop")
    Call<List<ExtinguisherModel>> getNewExtinguishers();
}
