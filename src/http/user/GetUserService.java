package http.user;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.AuthRequest;
import models.LoginRes;
import models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static utils.MainPanel.ACCESS_TOKEN;

public class GetUserService extends ServiceAPI {


    public IGetUser getService() {
        return getRetrofit().create(IGetUser.class);
    }
    public void getUser(String user, RequestCallback2 callback) {
        getService().getUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                  ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void init(AuthRequest body, RequestCallback2 callback2) {
        getService().login(body).enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if(response.isSuccessful()) {
                    callback2.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable throwable) {
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getUsers(RequestCallback callback) {
        getService().getUsers(ACCESS_TOKEN).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                  callback.callback(response.body());
                } else {
                  ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void createUser(User user, RequestCallback2 callback) {
        getService().createUser(user,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(0);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                 callback.callback(0);
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void deleteUser(String user, RequestCallback2 callback) {
        getService().deleteUser(user,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(0);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                callback.callback(0);
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
}
