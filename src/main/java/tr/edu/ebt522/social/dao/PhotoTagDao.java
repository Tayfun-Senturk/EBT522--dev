package tr.edu.ebt522.social.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tr.edu.ebt522.social.db.Db;
import tr.edu.ebt522.social.model.PhotoTag;

public final class PhotoTagDao {
  private PhotoTagDao() {}

  public static List<PhotoTag> listTags(long photoId) throws SQLException {
    String sql = "SELECT id, photo_id, tagged_user_id, shape, coords, label FROM photo_tags WHERE photo_id=? ORDER BY id";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, photoId);
      try (ResultSet rs = ps.executeQuery()) {
        List<PhotoTag> out = new ArrayList<>();
        while (rs.next()) {
          Long tagged = rs.getObject("tagged_user_id") == null ? null : rs.getLong("tagged_user_id");
          out.add(
              new PhotoTag(
                  rs.getLong("id"),
                  rs.getLong("photo_id"),
                  tagged,
                  rs.getString("shape"),
                  rs.getString("coords"),
                  rs.getString("label")));
        }
        return out;
      }
    }
  }

  public static long addTag(long photoId, Long taggedUserId, String shape, String coords, String label)
      throws SQLException {
    String sql = "INSERT INTO photo_tags (photo_id, tagged_user_id, shape, coords, label) VALUES (?,?,?,?,?)";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, photoId);
      if (taggedUserId == null) {
        ps.setNull(2, java.sql.Types.BIGINT);
      } else {
        ps.setLong(2, taggedUserId);
      }
      ps.setString(3, shape);
      ps.setString(4, coords);
      ps.setString(5, label);
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) return rs.getLong(1);
      }
      throw new SQLException("Etiket ID alınamadı.");
    }
  }

  public static void deleteTag(long tagId) throws SQLException {
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM photo_tags WHERE id=?")) {
      ps.setLong(1, tagId);
      ps.executeUpdate();
    }
  }
}

