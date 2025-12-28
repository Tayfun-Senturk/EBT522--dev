package tr.edu.ebt522.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import tr.edu.ebt522.social.db.Db;
import tr.edu.ebt522.social.model.Photo;

public final class PhotoDao {
  private PhotoDao() {}

  public static List<Photo> listPhotos() throws SQLException {
    String sql = "SELECT id, title, image_path, thumb_path, created_at FROM photos ORDER BY created_at DESC";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      List<Photo> out = new ArrayList<>();
      while (rs.next()) {
        OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
        out.add(
            new Photo(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("image_path"),
                rs.getString("thumb_path"),
                created.toInstant().toEpochMilli()));
      }
      return out;
    }
  }

  public static Photo getPhoto(long photoId) throws SQLException {
    String sql = "SELECT id, title, image_path, thumb_path, created_at FROM photos WHERE id=?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, photoId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
        return new Photo(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("image_path"),
            rs.getString("thumb_path"),
            created.toInstant().toEpochMilli());
      }
    }
  }

  public static long addPhoto(String title, String imagePath, String thumbPath, Long uploadedBy) throws SQLException {
    String sql = "INSERT INTO photos (title, image_path, thumb_path, uploaded_by) VALUES (?,?,?,?)";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, title);
      ps.setString(2, imagePath);
      ps.setString(3, thumbPath);
      if (uploadedBy == null) {
        ps.setNull(4, java.sql.Types.BIGINT);
      } else {
        ps.setLong(4, uploadedBy);
      }
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) return rs.getLong(1);
      }
      throw new SQLException("Fotoğraf ID alınamadı.");
    }
  }
}

