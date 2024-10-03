package http.sklad;

import exceptions.ErrorDialog;
import http.RequestCallback;
import http.RequestCallback2;
import http.base.ServiceAPI;
import models.ArtikulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static utils.MainPanel.ACCESS_TOKEN;

public class GetArtikulService extends ServiceAPI {

    public IGetArtikuls getService() {
        return getRetrofit().create(IGetArtikuls.class);
    }

    public void getArtikuls(boolean grey, boolean orderByDate, RequestCallback callback) {
        getService().getArtikuls(grey, orderByDate, ACCESS_TOKEN).enqueue(new Callback<List<ArtikulModel>>() {
            @Override
            public void onResponse(Call<List<ArtikulModel>> call, Response<List<ArtikulModel>> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(null);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ArtikulModel>> call, Throwable throwable) {
                   callback.callback(null);
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }

    public void inserArtikul(String table, ArtikulModel body, RequestCallback2 callback) {
        getService().insertArtikul(table, body,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
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

    public void deleteArtikul(String table, String artikul, String kontragent, String invoiceByKontragent, RequestCallback2 callback) {
        getService().deleteArtikul(table, artikul,kontragent,invoiceByKontragent,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
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

    public void renameArtikul(String table, String oldName, String newName, RequestCallback2 callback) {
        getService().renameArtikul(table, oldName,newName,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
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

    public void editArtikulQuantity(String table, String artikul, String kontragent, String invoiceByKontragent, String newQuantity, RequestCallback2 callback) {
        getService().editArtikulQuantity(table, artikul,kontragent, invoiceByKontragent, newQuantity,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
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

    public void editArtikulPrice(String table,String newValue, String percentProfit, String artikul, String kontragent, String invoiceByKontragent, RequestCallback2 callback) {
        getService().editArtikulPrice(table, newValue, percentProfit, artikul, kontragent, invoiceByKontragent,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
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
        getService().updatePartPrice(price,part,type,weight,category,ACCESS_TOKEN).enqueue(new Callback<Integer>() {
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

    public void getPartPrice(String part, String type, String category, String weight, RequestCallback2 callback) {
        getService().getPartPrice(part,type,category,weight,ACCESS_TOKEN).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful()) {
                    callback.callback(response.body());
                } else {
                    callback.callback(0d);
                    ErrorDialog.showHttpError(response);
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable throwable) {
                   callback.callback(0d);
                   ErrorDialog.showErrorMessage(throwable.getMessage());
            }
        });
    }
    public Double getPartPriceSynchronous(String part, String type, String category, String weight) {
        Response<Double> response = null;
        try {
            response = getService().getPartPrice(part,type,category,weight,ACCESS_TOKEN).execute();
        } catch (IOException e) {
            return 0d;
        }
        return response.body();
    }

    public void getArtikulValue(String table, String artikul, RequestCallback2 callback) {
        getService().getArtikulValue(table, artikul,ACCESS_TOKEN).enqueue(new Callback<Double>() {
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

    public Double getArtikulValueSyncronous(String table, String artikul) {
        Response<Double> maxPrice;
        try {
            maxPrice = getService().getArtikulValue(table,artikul,ACCESS_TOKEN).execute();
        } catch (IOException e) {
            return Double.NaN;
        }
        return maxPrice.body();
    }

    public void getExtinguisherValue(String type, String weight, String category, String brand, RequestCallback2 callback) {
        getService().getExtinguisherValue(type,weight,category,brand,ACCESS_TOKEN).enqueue(new Callback<Double>() {
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
