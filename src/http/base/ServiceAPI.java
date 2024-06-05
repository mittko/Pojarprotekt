package http.base;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ServiceAPI {

    private Retrofit retrofit;

    public Retrofit getRetrofit(){
        if(retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .writeTimeout(30,TimeUnit.SECONDS);

            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:8080")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
