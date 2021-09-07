package org.example.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"id"})
public class Comment {

    @JsonProperty("id")
    private int id;
    @JsonProperty("post")
    private int post;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("likes")
    private List<Integer> likes = new ArrayList<>();
    @JsonProperty("dislikes")
    private List<Integer> dislikes = new ArrayList<>();
    @JsonProperty("body")
    private String body;
    @JsonProperty("createdAt")
    private String createdAt;

    public Comment(int id, int post, String name, String email, List<Integer> likes, List<Integer> dislikes, String body, String createdAt) {
        this.id = id;
        this.post = post;
        this.name = name;
        this.email = email;
        this.likes = likes;
        this.dislikes = dislikes;
        this.body = body;
        this.createdAt = createdAt;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public Comment setId(int id) {
        this.id = id;
        return this;
    }

    public int getPost() {
        return post;
    }

    public Comment setPost(int post) {
        this.post = post;
        return this;
    }

    public String getName() {
        return name;
    }

    public Comment setName(String name) {
        this.name = name;
        return this;
    }

    public List<Integer> getLikes() {
        return likes;
    }

    public Comment setLikes(List<Integer> likes) {
        this.likes = likes;
        return this;
    }

    public List<Integer> getDislikes() {
        return dislikes;
    }

    public Comment setDislikes(List<Integer> dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Comment setBody(String body) {
        this.body = body;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Comment setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Comment setEmail(String email) {
        this.email = email;
        return this;
    }
}
