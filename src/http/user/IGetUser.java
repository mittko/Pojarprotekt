package http.user;

import models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IGetUser {

    @GET("/user_data")
    Call<User> getUser(@Query("user") String user);

    @GET("/users")
    Call<List<User>> getUsers();
}
