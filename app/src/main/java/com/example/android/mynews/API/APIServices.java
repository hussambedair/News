package com.example.android.mynews.API;

import com.example.android.mynews.API.Models.NewsResponse.NewsResponse;
import com.example.android.mynews.API.Models.SourcesResponse.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {

    @GET("sources")
    public Call<SourcesResponse> getSources(@Query("apiKey") String apiKey,
                                            @Query("language") String language);

    @GET("everything")
    public Call<NewsResponse> getNewsBySourceId(@Query("apiKey") String apiKey,
                                                @Query("language") String language,
                                                @Query("sources") String sourceId);


}
