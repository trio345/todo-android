package id.co.mdd.todoapp.utils;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtils {
    public static <T> T client(Class<T> service, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder().setLenient().create()
                        )
                )
                .build();

        return retrofit.create(service);
    }
}
