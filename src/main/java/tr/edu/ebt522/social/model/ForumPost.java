package tr.edu.ebt522.social.model;

public class ForumPost {
  private final long id;
  private final long userId;
  private final String authorName;
  private final String content;
  private final long createdAtMs;
  private final long updatedAtMs;

  public ForumPost(long id, long userId, String authorName, String content, long createdAtMs, long updatedAtMs) {
    this.id = id;
    this.userId = userId;
    this.authorName = authorName;
    this.content = content;
    this.createdAtMs = createdAtMs;
    this.updatedAtMs = updatedAtMs;
  }

  public long getId() {
    return id;
  }

  public long getUserId() {
    return userId;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getContent() {
    return content;
  }

  public long getCreatedAtMs() {
    return createdAtMs;
  }

  public long getUpdatedAtMs() {
    return updatedAtMs;
  }
}

