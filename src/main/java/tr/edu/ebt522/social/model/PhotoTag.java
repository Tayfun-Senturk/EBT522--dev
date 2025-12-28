package tr.edu.ebt522.social.model;

public class PhotoTag {
  private final long id;
  private final long photoId;
  private final Long taggedUserId;
  private final String shape;
  private final String coords;
  private final String label;

  public PhotoTag(long id, long photoId, Long taggedUserId, String shape, String coords, String label) {
    this.id = id;
    this.photoId = photoId;
    this.taggedUserId = taggedUserId;
    this.shape = shape;
    this.coords = coords;
    this.label = label;
  }

  public long getId() {
    return id;
  }

  public long getPhotoId() {
    return photoId;
  }

  public Long getTaggedUserId() {
    return taggedUserId;
  }

  public String getShape() {
    return shape;
  }

  public String getCoords() {
    return coords;
  }

  public String getLabel() {
    return label;
  }
}

