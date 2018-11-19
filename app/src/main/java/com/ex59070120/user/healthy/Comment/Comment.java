package com.ex59070120.user.healthy.Comment;

public class Comment {
    private int postId;
    private int commentId;
    private String commentBody;
    private String commentName;
    private String commentEmail;

    Comment(){ }
    Comment(int postId,int commentId,String commentName,String commentEmail){
        this.postId = postId;
        this.commentId = commentId;
        this.commentBody = commentBody;
        this.commentName = commentName;
        this.commentEmail =commentEmail;

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentBody() { return commentBody; }

    public void setCommentBody(String commentBody) { this.commentBody = commentBody; }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }
}
