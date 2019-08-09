package com.example.jiehui.chatrobot;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ChatService {
    @POST("/openapi/api")
    Observable<ListData> getTuLingChat(@Query("key") String key, @Query("info") String info);

}
