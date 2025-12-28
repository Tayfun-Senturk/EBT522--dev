package tr.edu.ebt522.social.model;

public class Video {
  private final long id;
  private final String title;
  private final String youtubeId;
  private final String thumbPath;
  private final long createdAtMs;

  public Video(long id, String title, String youtubeId, String thumbPath, long createdAtMs) {
    this.id = id;
    this.title = title;
    this.youtubeId = youtubeId;
    this.thumbPath = thumbPath;
    this.createdAtMs = createdAtMs;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getYoutubeId() {
    return youtubeId;
  }

  public String getThumbPath() {
    return thumbPath;
  }

  public long getCreatedAtMs() {
    return createdAtMs;
  }
}

