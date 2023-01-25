package com.typicode.app.typicode.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typicode.app.typicode.msgType.Comments;
import com.typicode.app.typicode.msgType.Post;
import com.typicode.app.typicode.msgType.User;

/*
    Helper class for generic/reusable actions
 */

public class TypicodeHelper {

    public static Post buildPost(int userId, String title, String body){

        return new Post(title,body,userId);
    }

    public static Comments updateComment(Comments comments, String fieldToChange,String value){

        switch(fieldToChange) {
            case "name":
                comments.setName(value);
                break;
            case "postId":
                comments.setPostId(Integer.parseInt(value));
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
                post.setUserId(Integer.parseInt(value));
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

    /*
        Genaric method to get JsonNode from an object-type.
        Can be used across User,Comments and Posts data type
     */
    public static <T> JsonNode getJsonNodeFromType(T expected){

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode=null;
        try {

            jsonNode = mapper.readTree(mapper.writeValueAsString(expected));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }

    public static String buildJsonStringWithGivenField(String fieldName, String fieldValuee){

        return  "{\n" +
                "\t\"" + fieldName + "\" : \"" + fieldValuee + "\"\n" +
                "\t\n" +
                "}";

    }

}
