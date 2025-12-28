package tr.edu.ebt522.social.model;

public class Photo {
  private final long id;
  private final String title;
  private final String imagePath;
  private final String thumbPath;
  private final long createdAtMs;

  public Photo(long id, String title, String imagePath, String thumbPath, long createdAtMs) {
    this.id = id;
    this.title = title;
    this.imagePath = imagePath;
    this.thumbPath = thumbPath;
    this.createdAtMs = createdAtMs;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getImagePath() {
    return imagePath;
  }

  public String getThumbPath() {
    return thumbPath;
  }

  public long getCreatedAtMs() {
    return createdAtMs;
  }
}

