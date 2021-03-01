package com.example.consumingrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
  
  private int userId;

  private int id;

  private String title;

  private String body;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Post)) return false;
    Post post = (Post) o;
    return id == post.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Post{" +
            "userId=" + userId +
            ", id=" + id +
            ", title='" + title + '\'' +
            ", body='" + body + '\'' +
            '}';
  }

  public JSONObject convertToJson() throws JSONException {
    JSONObject result = new JSONObject();

    result.put("userId", userId);
    result.put("id", id);
    result.put("title", title);
    result.put("body", body);

    return result;
  }
}