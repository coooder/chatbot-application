package com.example.jiehui.chatrobot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by jiehui on 6/6/17.
 */

public class NetworkUtils {
    private static final String Base_Url = "http://www.tuling123.com";
    private ChatService chatService;


    public NetworkUtils() {
        final Gson gson = new GsonBuilder().create();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        chatService = retrofit.create(ChatService.class);
    }

    public Observable<ListData> getTuLingChat(String key, String text) {
        return chatService.getTuLingChat(key, text);
    }
}
