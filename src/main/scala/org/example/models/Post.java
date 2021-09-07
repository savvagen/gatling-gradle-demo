package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    @JsonProperty("id")
    private int id;
    @JsonProperty("user")
    private int user;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("category")
    private String category;
    @JsonProperty("comments")
    private List<Integer> comments = new ArrayList<>();
    @JsonProperty("createdAt")
    private String createdAt;

    public Post(int id, int user, String title, String body, String subject, String category, List<Integer> comments, String createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.body = body;
        this.subject = subject;
        this.category = category;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public Post setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Post setBody(String body) {
        this.body = body;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Post setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Post setCategory(String category) {
        this.category = category;
        return this;
    }

    public List<Integer> getComments() {
        return comments;
    }

    public Post setComments(List<Integer> comments) {
        this.comments = comments;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Post setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public int getUser() {
        return user;
    }

    public Post setUser(int user) {
        this.user = user;
        return this;
    }
}
