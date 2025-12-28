package tr.edu.ebt522.social.model;

public class Comment {
  private final long id;
  private final long targetId;
  private final long authorUserId;
  private final String authorName;
  private final String content;
  private final long createdAtMs;
  private final long updatedAtMs;

  public Comment(
      long id,
      long targetId,
      long authorUserId,
      String authorName,
      String content,
      long createdAtMs,
      long updatedAtMs) {
    this.id = id;
    this.targetId = targetId;
    this.authorUserId = authorUserId;
    this.authorName = authorName;
    this.content = content;
    this.createdAtMs = createdAtMs;
    this.updatedAtMs = updatedAtMs;
  }

  public long getId() {
    return id;
  }

  public long getTargetId() {
    return targetId;
  }

  public long getAuthorUserId() {
    return authorUserId;
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

