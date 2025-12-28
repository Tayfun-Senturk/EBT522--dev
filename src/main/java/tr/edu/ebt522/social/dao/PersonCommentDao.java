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
import tr.edu.ebt522.social.model.Comment;

public final class PersonCommentDao {
  private PersonCommentDao() {}

  public static long addComment(long personUserId, long authorUserId, String content) throws SQLException {
    String sql = "INSERT INTO person_comments (person_user_id, author_user_id, content) VALUES (?,?,?)";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, personUserId);
      ps.setLong(2, authorUserId);
      ps.setString(3, content);
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) return rs.getLong(1);
      }
      throw new SQLException("Yorum ID alınamadı.");
    }
  }

  public static List<Comment> listComments(long personUserId) throws SQLException {
    String sql =
        "SELECT c.id, c.person_user_id, c.author_user_id, u.first_name, u.last_name, c.content, c.created_at, c.updated_at "
            + "FROM person_comments c "
            + "JOIN users u ON u.id = c.author_user_id "
            + "WHERE c.person_user_id = ? "
            + "ORDER BY c.created_at DESC";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, personUserId);
      try (ResultSet rs = ps.executeQuery()) {
        List<Comment> out = new ArrayList<>();
        while (rs.next()) {
          OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
          OffsetDateTime updated = rs.getObject("updated_at", OffsetDateTime.class);
          out.add(
              new Comment(
                  rs.getLong("id"),
                  rs.getLong("person_user_id"),
                  rs.getLong("author_user_id"),
                  (rs.getString("first_name") + " " + rs.getString("last_name")).trim(),
                  rs.getString("content"),
                  created.toInstant().toEpochMilli(),
                  updated.toInstant().toEpochMilli()));
        }
        return out;
      }
    }
  }

  public static Comment getComment(long commentId) throws SQLException {
    String sql =
        "SELECT c.id, c.person_user_id, c.author_user_id, u.first_name, u.last_name, c.content, c.created_at, c.updated_at "
            + "FROM person_comments c "
            + "JOIN users u ON u.id = c.author_user_id "
            + "WHERE c.id = ?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, commentId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        OffsetDateTime created = rs.getObject("created_at", OffsetDateTime.class);
        OffsetDateTime updated = rs.getObject("updated_at", OffsetDateTime.class);
        return new Comment(
            rs.getLong("id"),
            rs.getLong("person_user_id"),
            rs.getLong("author_user_id"),
            (rs.getString("first_name") + " " + rs.getString("last_name")).trim(),
            rs.getString("content"),
            created.toInstant().toEpochMilli(),
            updated.toInstant().toEpochMilli());
      }
    }
  }

  public static void updateComment(long commentId, String newContent) throws SQLException {
    String sql = "UPDATE person_comments SET content=?, updated_at=NOW() WHERE id=?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, newContent);
      ps.setLong(2, commentId);
      ps.executeUpdate();
    }
  }

  public static void deleteComment(long commentId) throws SQLException {
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM person_comments WHERE id=?")) {
      ps.setLong(1, commentId);
      ps.executeUpdate();
    }
  }
}

