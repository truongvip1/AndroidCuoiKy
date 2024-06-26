package com.example.doancuoiky.retrofit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient extends BaseClient{

    private static String BASE_URL = "https://us-central1-truong-ac24d.cloudfunctions.net/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static APIService getApiService(){
        return createService(APIService.class, BASE_URL);
    }
}