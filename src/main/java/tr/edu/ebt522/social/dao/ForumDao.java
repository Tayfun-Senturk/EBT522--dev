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
import tr.edu.ebt522.social.model.ForumPost;

public final class ForumDao {
  private ForumDao() {}

  public static long createPost(long userId, String content) throws SQLException {
    String sql = "INSERT INTO forum_posts (user_id, content) VALUES (?, ?)";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, userId);
      ps.setString(2, content);
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) return rs.getLong(1);
      }
      throw new SQLException("Forum post ID alınamadı.");
    }
  }

  public static int countPosts() throws SQLException {
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM forum_posts");
        ResultSet rs = ps.executeQuery()) {
      rs.next();
      return rs.getInt(1);
    }
  }

  public static List<ForumPost> listPosts(int page, int pageSize) throws SQLException {
    if (page < 1) page = 1;
    int offset = (page - 1) * pageSize;
    String sql =
        "SELECT p.id, p.user_id, u.first_name, u.last_name, p.content, p.created_at, p.updated_at "
            + "FROM forum_posts p "
            + "JOIN users u ON u.id = p.user_id "
            + "ORDER BY p.created_at DESC "
            + "LIMIT ? OFFSET ?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, pageSize);
      ps.setInt(2, offset);
      try (ResultSet rs = ps.executeQuery()) {
        List<ForumPost> out = new ArrayList<>();
        while (rs.next()) {
          OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
          OffsetDateTime updated = rs.getObject("updated_at", OffsetDateTime.class);
          out.add(
              new ForumPost(
                  rs.getLong("id"),
                  rs.getLong("user_id"),
                  (rs.getString("first_name") + " " + rs.getString("last_name")).trim(),
                  rs.getString("content"),
                  created.toInstant().toEpochMilli(),
                  updated.toInstant().toEpochMilli()));
        }
        return out;
      }
    }
  }

  public static ForumPost getPost(long postId) throws SQLException {
    String sql =
        "SELECT p.id, p.user_id, u.first_name, u.last_name, p.content, p.created_at, p.updated_at "
            + "FROM forum_posts p "
            + "JOIN users u ON u.id = p.user_id "
            + "WHERE p.id = ?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, postId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
        OffsetDateTime updated = rs.getObject("updated_at", OffsetDateTime.class);
        return new ForumPost(
            rs.getLong("id"),
            rs.getLong("user_id"),
            (rs.getString("first_name") + " " + rs.getString("last_name")).trim(),
            rs.getString("content"),
            created.toInstant().toEpochMilli(),
            updated.toInstant().toEpochMilli());
      }
    }
  }

  public static void updatePost(long postId, String newContent) throws SQLException {
    String sql = "UPDATE forum_posts SET content=?, updated_at=NOW() WHERE id=?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, newContent);
      ps.setLong(2, postId);
      ps.executeUpdate();
    }
  }

  public static void deletePost(long postId) throws SQLException {
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM forum_posts WHERE id=?")) {
      ps.setLong(1, postId);
      ps.executeUpdate();
    }
  }
}

