package com.devnimas.practiceretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private    JsonPlaceholderApi jsonPlaceholderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())     //to convert Gson to json
                .build();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        //showPosts();
        showComments();
    }

    private void showPosts() {
        Call<List<Post>> call = jsonPlaceholderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText(response.code());
                    return;
                }

                List<Post> posts = response.body();
                for (Post i : posts){
                    String concatinating  = "";
                    concatinating += "ID: "+ i.getId() + "\n";
                    concatinating += "User ID: "+ i.getUserId() + "\n";
                    concatinating += "Title: "+ i.getTitle() + "\n";
                    concatinating += "Text: "+ i.getText() + "\n\n";

                    tvResult.append(concatinating);
                }

            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }


    private void showComments() {
        Call<List<Comment>> call = jsonPlaceholderApi.getComments(1);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    tvResult.setText(response.code());
                    return;
                }

                List<Comment> comments = response.body();
                for (Comment i : comments){
                    String concatinating  = "";
                    concatinating += "Post ID: "+ i.getPostId() + "\n";
                    concatinating += "User ID: "+ i.getId() + "\n";
                    concatinating += "Name: "+ i.getName() + "\n";
                    concatinating += "Email" + i.getEmail() + "\n";
                    concatinating += "Text: "+ i.getText() + "\n\n";

                    tvResult.append(concatinating);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });

    }
}