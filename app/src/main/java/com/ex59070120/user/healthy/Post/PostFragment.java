package com.ex59070120.user.healthy.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ex59070120.user.healthy.Comment.CommentFragment;
import com.ex59070120.user.healthy.MenuFragment;
import com.ex59070120.user.healthy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PostFragment extends Fragment {

    ArrayList<Post> posts = new ArrayList<>();
    PostItem _postItem;
    ListView _postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _postItem = new PostItem(getActivity(), R.layout.fragment_post_item, posts);
        _postList = getView().findViewById(R.id.list_post);

        backCommentBtn();
        getPost();
    }

    public void backCommentBtn() {
        Button _backBtn = (Button) getView().findViewById(R.id.post_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null).commit();
                Log.d("POST", "GO TO MENU");
            }
        });
    }

    public void getPost(){
        final PostItem _postItem = new PostItem(getActivity(), R.layout.fragment_post_item, posts);
        OkHttpClient client = new OkHttpClient();
        String post_url = "https://jsonplaceholder.typicode.com/posts";

        Request request = new Request.Builder().url(post_url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<Collection<Post>>(){}.getType();
                    Collection<Post> post = gson.fromJson(myResponse, type);
                    Post[] postList = post.toArray(new Post[post.size()]);

                    for(int i=0 ; i < postList.length ; i++){
                        Post data = new Post();
                        int id = postList[i].getPost_id();
                        String title = postList[i].getPost_title();
                        String body = postList[i].getPost_body();
                        data.setPost_id(id);
                        data.setPost_title(title);
                        data.setPost_body(body);
                        posts.add(data);
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _postList.setAdapter(_postItem);
                        _postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Post post = posts.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putString("id",String.valueOf(post.getUserId()));

                                Fragment fragment = new CommentFragment();
                                fragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity()
                                                                    .getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.activity_main, fragment)
                                        .addToBackStack(null).commit();
                            }
                        });
                    }
                });
            }
        });
    }

}