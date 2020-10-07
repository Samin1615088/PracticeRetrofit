package com.devnimas.practiceretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        showPosts();
        //showComments();
        //showPostsWithMultipleParameter();
        //showPostsWithMap();
        //doPostWithPostObjectBody();
        //doPostUsingFromUrlEncoded();
        //doPostUsingFieldMap();
        //doUpdatePostUsingPut();
        //doUpdatePostUsingPatch();
        //doDeletePost();

    }

    private void showPosts() {
        Call<List<Post>> call = jsonPlaceholderApi.getAllPosts();

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
        Call<List<Comment>> call = jsonPlaceholderApi.getCommentsWithOnlyOneParameter(1);

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


    private void showPostsWithMultipleParameter() {
        Call<List<Post>> call = jsonPlaceholderApi.getPostWithMultipleParameter(1, "id", "desc");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText(response.code());
                    return;
                }

                List<Post> posts = response.body();
                Toast.makeText(getApplicationContext(),""+posts.size(), Toast.LENGTH_LONG).show();
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


    private void showPostsWithMap() {
        Map<String, String> map = new HashMap< >();
        map.put("userId", "1");
        map.put("_sort", "id");
        map.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceholderApi.getPostsUsingMap(map);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText(response.code());
                    return;
                }

                List<Post> posts = response.body();
                Toast.makeText(getApplicationContext(),""+posts.size(), Toast.LENGTH_LONG).show();
                for (Post i : posts){
                    String concatenating  = "";
                    concatenating += "ID: "+ i.getId() + "\n";
                    concatenating += "User ID: "+ i.getUserId() + "\n";
                    concatenating += "Title: "+ i.getTitle() + "\n";
                    concatenating += "Text: "+ i.getText() + "\n\n";

                    tvResult.append(concatenating);
                }

            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }


    private void doPostWithPostObjectBody() {
        Post post = new Post(23, "new Title", "new Text");

        Call<Post> call = jsonPlaceholderApi.createPostWithPostObjectBody(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvResult.setText(response.code());
                    return;
                }

                Post postResponse = response.body();

                String concatinating  = "";
                concatinating += "Code: "+ response.code() + "\n";
                concatinating += "ID: "+ postResponse.getId() + "\n";
                concatinating += "User ID: "+ postResponse.getUserId() + "\n";
                concatinating += "Title: "+ postResponse.getTitle() + "\n";
                concatinating += "Text: "+ postResponse.getText() + "\n\n";

                tvResult.setText(concatinating);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }


    private void doPostUsingFromUrlEncoded() {
        Call<Post> call = jsonPlaceholderApi.createPostUsingFromUrlEncoded(23,"new Title", "new Text");

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvResult.setText(response.code());
                    return;
                }

                Post postResponse = response.body();

                String concatinating  = "";
                concatinating += "Code: "+ response.code() + "\n";
                concatinating += "ID: "+ postResponse.getId() + "\n";
                concatinating += "User ID: "+ postResponse.getUserId() + "\n";
                concatinating += "Title: "+ postResponse.getTitle() + "\n";
                concatinating += "Text: "+ postResponse.getText() + "\n\n";

                tvResult.setText(concatinating);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }


    private void doPostUsingFieldMap() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "23");
        map.put("title", "new Title");
        map.put("body", "new Text");


        Call<Post> call = jsonPlaceholderApi.createPostUsingFieldMap(map);


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvResult.setText(response.code());
                    return;
                }

                Post postResponse = response.body();

                String concatinating  = "";
                concatinating += "Code: "+ response.code() + "\n";
                concatinating += "ID: "+ postResponse.getId() + "\n";
                concatinating += "User ID: "+ postResponse.getUserId() + "\n";
                concatinating += "Title: "+ postResponse.getTitle() + "\n";
                concatinating += "Text: "+ postResponse.getText() + "\n\n";

                tvResult.setText(concatinating);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void doUpdatePostUsingPut() {
        Post post = new Post(12, "New Title", "New Text");
        Call<Post> call = jsonPlaceholderApi.putPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    tvResult.setText("Code: " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                tvResult.setText(content);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void doUpdatePostUsingPatch() {
        Post post = new Post(12, null, "New Text");
        Call<Post> call = jsonPlaceholderApi.patchPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    tvResult.setText("Code: " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                tvResult.setText(content);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    private void doDeletePost() {
        Call<Void> call = jsonPlaceholderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                tvResult.setText("Code: " + response.code());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }
}