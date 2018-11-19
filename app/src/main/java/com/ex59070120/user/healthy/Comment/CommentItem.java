package com.ex59070120.user.healthy.Comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ex59070120.user.healthy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CommentItem extends ArrayAdapter {

    ArrayList<JSONObject> comments = new ArrayList<>();
    Context context;

    public CommentItem(Context context, int resouce, ArrayList<JSONObject> object){
        super(context,resouce,object);
        this.context = context;
        this.comments = object;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        View commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);
        JSONObject commentObj = comments.get(position);
        TextView commentPostId = commentItem.findViewById(R.id.post_id);
        TextView commentId = commentItem.findViewById(R.id.comment_id);
        TextView commentBody  = commentItem.findViewById(R.id.comment_body);
        TextView commentName = commentItem.findViewById(R.id.comment_name);
        TextView commentEmail  = commentItem.findViewById(R.id.comment_email);
        try
        {
            commentPostId.setText(commentObj.getString("postId"));
            commentId.setText(commentObj.getString("id"));
            commentBody.setText(commentObj.getString("body"));
            commentName.setText("name : " + commentObj.getString("name"));
            commentEmail.setText("email : " + commentObj.getString("email"));
        }
        catch (JSONException e)
        {
            Log.d("test", "catch JSONException : " + e.getMessage());
        }
        return commentItem;
    }
}
