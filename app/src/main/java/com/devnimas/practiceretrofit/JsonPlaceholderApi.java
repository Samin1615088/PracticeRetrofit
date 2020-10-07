package com.devnimas.practiceretrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonPlaceholderApi {

    //last part of the url request
    @GET("posts")
    Call<List<Post>> getAllPosts();

    @GET("posts/{id}/comments")
    Call<List<Comment>> getCommentsWithOnlyOneParameter(@Path("id") int postId);

    @GET("posts")    //sample url = "https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc"
    Call<List<Post>> getPostWithMultipleParameter(      //? and _ is added by retrofit automatically
            @Query("userId") Integer userId,
            @Query("_sort") String sort,
            @Query("_order") String order
            );
    //same as before except we can use multiple userId in calling
    /*Call<List<Post>> getPostWithMultipleParameter(      //? and _ is added by retrofit automatically
                                                        @Query("sort") String sort,
                                                        @Query("order") String order,
                                                        @Query("userId") Integer...userId2
    );*/

    //get data from server
    @GET("posts")                           //sample url = "https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc"
    Call<List<Post>> getPostsUsingMap(@QueryMap Map<String, String> parameters);    //? and _ is added by retrofit automatically

    @POST("posts")
    Call<Post> createPostWithPostObjectBody(@Body Post post);

    //create data in server
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPostUsingFromUrlEncoded(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String body
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPostUsingFieldMap(@FieldMap Map<String,String> parameters);


    //update data in server
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    //delete data in server
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
