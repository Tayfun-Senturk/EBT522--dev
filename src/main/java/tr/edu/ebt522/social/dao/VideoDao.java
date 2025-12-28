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
import tr.edu.ebt522.social.model.Video;

public final class VideoDao {
  private VideoDao() {}

  public static List<Video> listVideos() throws SQLException {
    String sql = "SELECT id, title, youtube_id, thumb_path, created_at FROM videos ORDER BY created_at DESC";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      List<Video> out = new ArrayList<>();
      while (rs.next()) {
        OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
        out.add(
            new Video(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("youtube_id"),
                rs.getString("thumb_path"),
                created.toInstant().toEpochMilli()));
      }
      return out;
    }
  }

  public static Video getVideo(long videoId) throws SQLException {
    String sql = "SELECT id, title, youtube_id, thumb_path, created_at FROM videos WHERE id=?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, videoId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
        return new Video(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("youtube_id"),
            rs.getString("thumb_path"),
            created.toInstant().toEpochMilli());
      }
    }
  }

  public static long addVideo(String title, String youtubeId, String thumbPath, Long createdBy) throws SQLException {
    String sql = "INSERT INTO videos (title, youtube_id, thumb_path, created_by) VALUES (?,?,?,?)";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, title);
      ps.setString(2, youtubeId);
      ps.setString(3, thumbPath);
      if (createdBy == null) {
        ps.setNull(4, java.sql.Types.BIGINT);
      } else {
        ps.setLong(4, createdBy);
      }
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) return rs.getLong(1);
      }
      throw new SQLException("Video ID alınamadı.");
    }
  }
}

