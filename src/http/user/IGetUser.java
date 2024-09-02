package http.user;

import models.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IGetUser {

    @GET("/user_data")
    Call<User> getUser(@Query("user") String user);

    @GET("/users")
    Call<List<User>> getUsers();

    @POST("/create_user")
    Call<Integer> createUser(@Body User user);

    @DELETE("/delete_user/{user}")
    Call<Integer> deleteUser(@Path("user") String user);
}
