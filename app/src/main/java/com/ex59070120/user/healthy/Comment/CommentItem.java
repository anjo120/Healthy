package com.ex59070120.user.healthy.Comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ex59070120.user.healthy.R;
import java.util.ArrayList;
import java.util.List;

public class CommentItem extends ArrayAdapter<Comment> {

    List<Comment> comments = new ArrayList<>();
    Context context;

    public CommentItem(Context context, int resouce, ArrayList<Comment> object){
        super(context,resouce,object);
        this.context = context;
        this.comments = object;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        View _commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);
        TextView _post_id = (TextView) _commentItem.findViewById(R.id.post_id);
        TextView _comment_id = (TextView) _commentItem.findViewById(R.id.comment_id);
        TextView _comment_body = (TextView) _commentItem.findViewById(R.id.comment_body);
        TextView _comment_name = (TextView) _commentItem.findViewById(R.id.comment_name);
        TextView _comment_email = (TextView) _commentItem.findViewById(R.id.comment_email);

        Comment _row = comments.get(position);
        _post_id.setText(String.valueOf(_row.getPostId()));
        _comment_id.setText(String.valueOf(_row.getCommentId()));
        _comment_body.setText(_row.getCommentBody());
        _comment_name.setText(_row.getCommentName());
        _comment_email.setText(_row.getCommentEmail());

        return _commentItem;
    }
}
