package http.sklad;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.ArtikulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class GetArtikulService extends ServiceAPI {

    public IGetArtikuls getService() {
        return getRetrofit().create(IGetArtikuls.class);
    }

    public void getArtikuls(boolean grey, boolean orderByDate, RequestCallback callback) {
        getService().getArtikuls(grey, orderByDate).enqueue(new Callback<List<ArtikulModel>>() {
            @Override
            public void onResponse(Call<List<ArtikulModel>> call, Response<List<ArtikulModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ArtikulModel>> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void inserArtikul(ArtikulModel body, RequestCallback2 callback) {
        getService().insertArtikul(body).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                 ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void deleteArtikul(String artikul, String kontragent, String invoiceByKontragent, RequestCallback2 callback) {
        getService().deleteArtikul(artikul,kontragent,invoiceByKontragent).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void renameArtikul(String oldName, String newName, RequestCallback2 callback) {
        getService().renameArtikul(oldName,newName).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void editArtikulQuantity(String artikul, String kontragent, String invoiceByKontragent, String newQuantity, RequestCallback2 callback) {
        getService().editArtikulQuantity(artikul,kontragent, invoiceByKontragent, newQuantity).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void editArtikulPrice(String newValue, String percentProfit, String artikul, String kontragent, String invoiceByKontragent, RequestCallback2 callback) {
        getService().editArtikulPrice(newValue, percentProfit, artikul, kontragent, invoiceByKontragent).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void updatePartPrice(String price, String part, String type, String weight, String category, RequestCallback2 callback) {
        getService().updatePartPrice(price,part,type,weight,category).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getPartPrice(String part, String type, String category, String weight, RequestCallback2 callback) {
        getService().getPartPrice(part,type,category,weight).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getArtikulDeliverValue(String table, String artikul, RequestCallback2 callback) {
        getService().getArtikulValue(table, artikul).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable throwable) {
                  ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void getExtinguisherValue(String type, String weight, String category, String brand, RequestCallback2 callback) {
        getService().getExtinguisherValue(type,weight,category,brand).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable throwable) {
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
}
