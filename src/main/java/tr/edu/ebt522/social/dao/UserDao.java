package tr.edu.ebt522.social.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import tr.edu.ebt522.social.db.Db;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.model.UserProfile;

public final class UserDao {
  private UserDao() {}

  public static final class AuthRecord {
    public final User user;
    public final String passwordStored;

    public AuthRecord(User user, String passwordStored) {
      this.user = user;
      this.passwordStored = passwordStored;
    }
  }

  public static long createUser(
      String username,
      String email,
      String passwordStored,
      String passwordSaltB64,
      int passwordIterations,
      String firstName,
      String lastName,
      String gender,
      List<String> hobbies,
      String city,
      java.time.LocalDate birthDate,
      String phone,
      String address,
      String about,
      String currentSchool,
      String currentJob,
      String webUrl,
      String facebookId,
      String twitterHandle,
      String avatarPath)
      throws SQLException {
    String sql =
        "INSERT INTO users "
            + "(username,email,password_hash,password_salt,password_iterations,first_name,last_name,gender,hobbies,city,birth_date,phone,address,about,current_school,current_job,web_url,facebook_id,twitter_handle,avatar_path,role) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 'USER')";

    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, username);
      ps.setString(2, email);
      ps.setString(3, passwordStored);
      ps.setString(4, passwordSaltB64);
      ps.setInt(5, passwordIterations);
      ps.setString(6, firstName);
      ps.setString(7, lastName);
      ps.setString(8, gender);
      if (hobbies == null || hobbies.isEmpty()) {
        ps.setNull(9, java.sql.Types.ARRAY);
      } else {
        Array arr = c.createArrayOf("text", hobbies.toArray(new String[0]));
        ps.setArray(9, arr);
      }
      ps.setString(10, city);
      if (birthDate != null) {
        ps.setDate(11, Date.valueOf(birthDate));
      } else {
        ps.setNull(11, java.sql.Types.DATE);
      }
      ps.setString(12, phone);
      ps.setString(13, address);
      ps.setString(14, about);
      ps.setString(15, currentSchool);
      ps.setString(16, currentJob);
      ps.setString(17, webUrl);
      ps.setString(18, facebookId);
      ps.setString(19, twitterHandle);
      ps.setString(20, avatarPath);

      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) return rs.getLong(1);
      }
      throw new SQLException("Kullanıcı ID alınamadı.");
    }
  }

  public static boolean usernameExists(String username) throws SQLException {
    String sql = "SELECT 1 FROM users WHERE lower(username)=lower(?)";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    }
  }

  public static boolean emailExists(String email) throws SQLException {
    String sql = "SELECT 1 FROM users WHERE lower(email)=lower(?)";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    }
  }

  public static AuthRecord findForLogin(String usernameOrEmail) throws SQLException {
    String sql =
        "SELECT id, username, first_name, last_name, role, password_hash "
            + "FROM users "
            + "WHERE lower(username)=lower(?) OR lower(email)=lower(?)";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, usernameOrEmail);
      ps.setString(2, usernameOrEmail);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        User user =
            new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("role"));
        return new AuthRecord(user, rs.getString("password_hash"));
      }
    }
  }

  public static List<UserProfile> listAllProfiles() throws SQLException {
    String sql =
        "SELECT id, username, email, first_name, last_name, role, gender, hobbies, city, birth_date, phone, address, about, current_school, current_job, web_url, facebook_id, twitter_handle, avatar_path "
            + "FROM users "
            + "ORDER BY lower(first_name), lower(last_name)";
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      List<UserProfile> out = new ArrayList<>();
      while (rs.next()) {
        out.add(readUserProfile(rs));
      }
      return out;
    }
  }

  public static UserProfile getProfileById(long userId) throws SQLException {
    String sql =
        "SELECT id, username, email, first_name, last_name, role, gender, hobbies, city, birth_date, phone, address, about, current_school, current_job, web_url, facebook_id, twitter_handle, avatar_path "
            + "FROM users WHERE id=?";
    try (Connection c = Db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setLong(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        return readUserProfile(rs);
      }
    }
  }

  public static void deleteUser(long userId) throws SQLException {
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE id=?")) {
      ps.setLong(1, userId);
      ps.executeUpdate();
    }
  }

  public static void setRole(long userId, String role) throws SQLException {
    try (Connection c = Db.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE users SET role=?, updated_at=NOW() WHERE id=?")) {
      ps.setString(1, role);
      ps.setLong(2, userId);
      ps.executeUpdate();
    }
  }

  private static UserProfile readUserProfile(ResultSet rs) throws SQLException {
    List<String> hobbies = Collections.emptyList();
    Array hobbyArr = rs.getArray("hobbies");
    if (hobbyArr != null) {
      Object raw = hobbyArr.getArray();
      if (raw instanceof String[]) {
        hobbies = Arrays.asList((String[]) raw);
      }
    }
    java.time.LocalDate birth = null;
    Date bd = rs.getDate("birth_date");
    if (bd != null) birth = bd.toLocalDate();

    return new UserProfile(
        rs.getLong("id"),
        rs.getString("username"),
        rs.getString("email"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("role"),
        rs.getString("gender"),
        hobbies,
        rs.getString("city"),
        birth,
        rs.getString("phone"),
        rs.getString("address"),
        rs.getString("about"),
        rs.getString("current_school"),
        rs.getString("current_job"),
        rs.getString("web_url"),
        rs.getString("facebook_id"),
        rs.getString("twitter_handle"),
        rs.getString("avatar_path"));
  }
}

