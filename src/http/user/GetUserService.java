package http.user;

import http.RequestCallback2;
import http.base.ServiceAPI;
import models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserService extends ServiceAPI {

    public IGetUser getService() {
        return getRetrofit().create(IGetUser.class);
    }
    public void getUser(String user, RequestCallback2 callback) {
        getService().getUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    callback.callback( response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
    }
}
