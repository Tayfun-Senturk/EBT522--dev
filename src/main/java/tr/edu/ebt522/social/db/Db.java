package tr.edu.ebt522.social.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public final class Db {
  private Db() {}

  private static String firstNonBlank(String... candidates) {
    if (candidates == null) return null;
    for (String c : candidates) {
      if (c != null && !c.trim().isEmpty()) return c.trim();
    }
    return null;
  }

  private static final class DbInfo {
    final String jdbcUrl;
    final String user;
    final String password;

    DbInfo(String jdbcUrl, String user, String password) {
      this.jdbcUrl = jdbcUrl;
      this.user = user;
      this.password = password;
    }
  }

  private static DbInfo resolve() {
    String rawUrl =
        firstNonBlank(
            System.getProperty("db.url"),
            System.getenv("DB_URL"),
            System.getenv("JDBC_DATABASE_URL"),
            System.getenv("DATABASE_URL"));

    String user = firstNonBlank(System.getProperty("db.user"), System.getenv("DB_USER"));
    String password = firstNonBlank(System.getProperty("db.password"), System.getenv("DB_PASSWORD"));

    if (rawUrl != null) {
      DbInfo parsed = parseDatabaseUrl(rawUrl);
      if (parsed != null) {
        if (user == null) user = parsed.user;
        if (password == null) password = parsed.password;
        return new DbInfo(parsed.jdbcUrl, firstNonBlank(user, "postgres"), firstNonBlank(password, "postgres"));
      }
    }

    String fallbackUrl = "jdbc:postgresql://localhost:5432/ebt522_social";
    return new DbInfo(fallbackUrl, firstNonBlank(user, "postgres"), firstNonBlank(password, "postgres"));
  }

  private static DbInfo parseDatabaseUrl(String raw) {
    if (raw == null) return null;
    String s = raw.trim();
    if (s.isEmpty()) return null;

    if (s.startsWith("jdbc:")) {
      return new DbInfo(s, null, null);
    }
    if (!(s.startsWith("postgres://") || s.startsWith("postgresql://"))) {
      return null;
    }

    URI uri = URI.create(s);
    String host = uri.getHost();
    int port = uri.getPort() == -1 ? 5432 : uri.getPort();
    String path = uri.getPath();
    String db = (path == null) ? "" : path;
    if (db.startsWith("/")) db = db.substring(1);
    if (db.isEmpty()) db = "postgres";

    String user = null;
    String password = null;
    String userInfo = uri.getUserInfo();
    if (userInfo != null && !userInfo.isBlank()) {
      String[] parts = userInfo.split(":", 2);
      user = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
      if (parts.length == 2) password = URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
    }

    StringBuilder jdbc = new StringBuilder();
    jdbc.append("jdbc:postgresql://").append(host).append(":").append(port).append("/").append(db);
    if (uri.getQuery() != null && !uri.getQuery().isBlank()) {
      jdbc.append("?").append(uri.getQuery());
    }
    return new DbInfo(jdbc.toString(), user, password);
  }

  public static Connection getConnection() throws SQLException {
    DbInfo info = resolve();
    return DriverManager.getConnection(info.jdbcUrl, info.user, info.password);
  }
}
