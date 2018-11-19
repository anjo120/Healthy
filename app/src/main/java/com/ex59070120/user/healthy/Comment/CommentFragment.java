package com.ex59070120.user.healthy.Comment;

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
import android.widget.ListView;

import com.ex59070120.user.healthy.Post.PostFragment;
import com.ex59070120.user.healthy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommentFragment extends Fragment {
    ArrayList<Comment> comments = new ArrayList<>();
    ListView _commentList;
    CommentItem _commentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _commentList = (ListView) getView().findViewById(R.id.list_comment);
        _commentItem = new CommentItem(getActivity(), R.layout.fragment_comment_item, comments);
        backPostBtn();
        getPostId();
    }

    public void backPostBtn() {
        Button _backBtn = (Button) getView().findViewById(R.id.comment_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new PostFragment())
                        .addToBackStack(null).commit();
                Log.d("COMMENT", "GO TO POST");
            }
        });
    }
    public void getPostId(){
        Bundle bundle = this.getArguments();
        String id = getArguments().getString("id");
        getComment(id);
        Log.d("COMMENT", "IN POST ID "+id);

    }

    public void getComment(String id){
        OkHttpClient client = new OkHttpClient();
        String comment_url = "https://jsonplaceholder.typicode.com/posts/"+id+"/comments";

        Request request = new Request.Builder().url(comment_url).build();
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
                    Type type = new TypeToken<Collection<Comment>>(){}.getType();
                    Collection<Comment> comment = gson.fromJson(myResponse, type);
                    Comment[] commentList = comment.toArray(new Comment[comment.size()]);

                    for(int i=0 ; i < commentList.length ; i++){
                        Comment data = new Comment();
                        int postId = commentList[i].getPostId();
                        int id = commentList[i].getCommentId();
                        String body = commentList[i].getCommentBody();
                        String name = commentList[i].getCommentName();
                        String email = commentList[i].getCommentEmail();
                        data.setPostId(postId);
                        data.setCommentId(id);
                        data.setCommentBody(body);
                        data.setCommentName(name);
                        data.setCommentEmail(email);
                        comments.add(data);
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _commentList.setAdapter(_commentItem);
                    }
                });
            }
        });
    }
}
