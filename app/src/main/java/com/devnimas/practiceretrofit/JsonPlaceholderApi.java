package com.devnimas.practiceretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    //last part of the url request
    @GET("posts")
    Call<List<Post>> getAllPosts();
}
