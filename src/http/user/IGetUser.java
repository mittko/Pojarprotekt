package http.user;

import models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetUser {

    @GET("/user_data")
    Call<User> getUser(@Query("user") String user);
}
