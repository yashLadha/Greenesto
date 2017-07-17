package io.github.yashladha.project.Models;


public class ChatMessage {
  private String body;
  private String userUid;
  private String imageUrl;

  public ChatMessage(String body, String userUid, String imageUrl) {
    this.body = body;
    this.userUid = userUid;
    this.imageUrl = imageUrl;
  }

  public ChatMessage(String body, String userUid) {
    this.body = body;
    this.userUid = userUid;
    this.imageUrl = "";
  }

  public ChatMessage() {
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getUserUid() {
    return userUid;
  }

  public void setUserUid(String userUid) {
    this.userUid = userUid;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
