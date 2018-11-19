package com.ex59070120.user.healthy.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ex59070120.user.healthy.R;
import com.ex59070120.user.healthy.Sleep.Sleep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PostItem extends ArrayAdapter {

    ArrayList<JSONObject> posts = new ArrayList<>();
    Context context;

    public PostItem(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.posts = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);
        JSONObject postObj = posts.get(position);
        TextView postTitle = postItem.findViewById(R.id.post_title);
        TextView postBody  = postItem.findViewById(R.id.post_body);
        try
        {
            postTitle.setText(postObj.getString("id") + " : " + postObj.getString("title"));
            postBody.setText(postObj.getString("body"));
        }
        catch (JSONException e)
        {
            Log.d("test", "catch JSONException : " + e.getMessage());
        }
        return postItem;
    }
}
