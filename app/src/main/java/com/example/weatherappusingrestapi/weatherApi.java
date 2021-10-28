package com.example.weatherappusingrestapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherApi {

    // https://api.openweathermap.org/data/2.5/weather?q=london&appid=
    @GET("weather")
    Call<Example> getWeather(@Query("q") String  cityName,
                             @Query("appid") String apiKey
                             );
}
