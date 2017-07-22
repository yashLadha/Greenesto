package io.github.yashladha.project.Models;

public class StorageFiles {
  private String fileUri;
  private String thumbnailUri;

  public StorageFiles(String fileUri, String thumbnailUri) {
    this.fileUri = fileUri;
    this.thumbnailUri = thumbnailUri;
  }

  public String getFileUri() {
    return fileUri;
  }

  public void setFileUri(String fileUri) {
    this.fileUri = fileUri;
  }

  public String getThumbnailUri() {
    return thumbnailUri;
  }

  public void setThumbnailUri(String thumbnailUri) {
    this.thumbnailUri = thumbnailUri;
  }
}
