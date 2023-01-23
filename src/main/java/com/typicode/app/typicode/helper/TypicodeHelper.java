package com.typicode.app.typicode.helper;

import com.typicode.app.typicode.msgType.Comments;
import com.typicode.app.typicode.msgType.Post;
import com.typicode.app.typicode.msgType.User;

/**
 * @author Vinod Kris
 */
public class TypicodeHelper {

    public static Post buildPost(String userId, String title, String body){

        return new Post(title,body,userId);
    }

    public static Comments updateComment(Comments comments, String fieldToChange,String value){

        switch(fieldToChange) {
            case "name":
                comments.setName(value);
                break;
            case "postId":
                comments.setPostId(value);
                break;
            case "email":
                comments.setEmail(value);
                break;
            case "body" :
                comments.setBody(value);
                break;
            default:
                break;
        }

        return comments;
    }

    public static Post updatePost(Post post,String fieldToChange,String value ){

        switch(fieldToChange) {
            case "userId":
                post.setUserId(value);
                break;
            case "title":
                post.setTitle(value);
                break;
            case "body" :
                post.setBody(value);
                break;
            default:
                break;
        }
        return post;
    }

    public static User updateUser(User user, String fieldToChange, String value ){

        switch(fieldToChange) {
            case "username":
                user.setUsername(value);
                break;
            case "name":
                user.setName(value);
                break;
            case "email" :
                user.setEmail(value);
                break;
            case "phone" :
                user.setPhone(value);
                break;
            case "website" :
                user.setWebsite(value);
                break;
            default:
                break;
        }
        return user;
    }

}
