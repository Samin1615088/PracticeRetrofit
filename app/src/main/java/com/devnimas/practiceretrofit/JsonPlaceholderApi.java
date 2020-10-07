package com.devnimas.practiceretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceholderApi {

    //last part of the url request
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int id);
}
