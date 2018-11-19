package com.ex59070120.user.healthy.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ex59070120.user.healthy.Comment.CommentFragment;
import com.ex59070120.user.healthy.MenuFragment;
import com.ex59070120.user.healthy.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PostFragment extends Fragment {
    String rs;
    JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder().url("https://jsonplaceholder.typicode.com/posts").build();

                    Response response = client.newCall(request).execute();
                    rs = response.body().string();
                    jsonArray = new JSONArray(rs);
                }
                catch (IOException e)
                {
                    Log.d("test", "catch IOException : " + e.getMessage());
                }
                catch (JSONException e)
                {
                    Log.d("test", "catch JSONException : " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                try
                {
                    final ArrayList<JSONObject> posts = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        posts.add(obj);
                    }
                    /*
                    ProgressBar progressBar = getView().findViewById(R.id.post_progress_bar);
                    progressBar.setVisibility(View.GONE);*/
                    ListView postListView = getView().findViewById(R.id.list_post);
                    PostItem postAdapter = new PostItem(getContext(), R.layout.fragment_post_item, posts);
                    postListView.setAdapter(postAdapter);
                    postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try{
                                Bundle bundle = new Bundle();
                                bundle.putInt("postId", posts.get(position).getInt("id"));
                                Log.d("POST", "postId => " + posts.get(position).getString("id"));
                                CommentFragment commentFragment = new CommentFragment();
                                commentFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view, commentFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            catch (JSONException e)
                            {
                                Log.d("test", "catch JSONException : " + e.getMessage());
                            }
                        }
                    });
                }
                catch (JSONException e)
                {
                    Log.d("test", "catch JSONException : " + e.getMessage());
                }
            }
        };
        task.execute();
    }

}