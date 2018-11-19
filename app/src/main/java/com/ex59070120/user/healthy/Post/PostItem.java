package com.ex59070120.user.healthy.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ex59070120.user.healthy.R;
import com.ex59070120.user.healthy.Sleep.Sleep;

import java.util.ArrayList;
import java.util.List;

public class PostItem extends ArrayAdapter<Post> {

    List<Post> posts = new ArrayList<>();
    Context context;

    public PostItem(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
        this.posts = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);
        TextView _post_id = (TextView) _postItem.findViewById(R.id.post_id);
        TextView _post_title = (TextView) _postItem.findViewById(R.id.post_title);
        TextView _post_body = (TextView) _postItem.findViewById(R.id.post_body);

        Post _row = posts.get(position);
        _post_id.setText(String.valueOf(_row.getPost_id()));
        _post_title.setText(_row.getPost_title());
        _post_body.setText(_row.getPost_body());

        return _postItem;
    }
}
