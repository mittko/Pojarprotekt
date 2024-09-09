package http.user;

import models.AuthRequest;
import models.LoginRes;
import models.User;
import retrofit2.Call;
import retrofit2.http.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface IGetUser {

    @POST("/login")
    Call<LoginRes> login(@Body AuthRequest body);

    @GET("/user_data")
    Call<User> getUser(@Query("user") String user);

    @GET("/users")
    Call<List<User>> getUsers();

    @POST("/create_user")
    Call<Integer> createUser(@Body User user);

    @DELETE("/delete_user/{user}")
    Call<Integer> deleteUser(@Path("user") String user);
}
