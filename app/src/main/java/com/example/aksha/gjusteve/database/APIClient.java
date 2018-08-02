package com.example.aksha.gjusteve.database;

import android.support.annotation.NonNull;

import com.example.aksha.gjusteve.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;


public class APIClient implements Interceptor {

    private  static final String Cache_Control ="cache-control";

    public static Retrofit getClient(String url){

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .build();
    }

    private static OkHttpClient okHttpClient(){

        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .cache(provideCache())
                .build();
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

    private static Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request =chain.request();
            if(!ConnectionCheck.isOnline(App.getContext()))
            {
                CacheControl cacheControl = new CacheControl.Builder().maxStale(7, TimeUnit.DAYS).build();
                request = request.newBuilder().cacheControl(cacheControl).build();
            }
            else {
                request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
            }
            return chain.proceed(request);
        };
    }

    private static Interceptor provideCacheInterceptor(){
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder().maxStale(2,TimeUnit.DAYS).build();

            return response.newBuilder().header(Cache_Control,cacheControl.toString()).build();
        };

    }

    private static Cache provideCache(){

        Cache cache = null;

        try {
            cache = new Cache(new File(App.getContext().getCacheDir(),"http-cache"),10*1024*1024);

        }
        catch (Exception e)
        {
            System.out.println("Error " + e.getMessage());
        }
        return cache;
    }


    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        if (!ConnectionCheck.isOnline(App.getContext())) {

            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
