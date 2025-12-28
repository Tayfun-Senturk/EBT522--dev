package tr.edu.ebt522.social.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    String normalizedQuery = normalizeQueryForJdbc(uri.getQuery());
    if (normalizedQuery != null && !normalizedQuery.isBlank()) {
      jdbc.append("?").append(normalizedQuery);
    }
    return new DbInfo(jdbc.toString(), user, password);
  }

  private static String normalizeQueryForJdbc(String rawQuery) {
    if (rawQuery == null) return null;
    String q = rawQuery.trim();
    if (q.isEmpty()) return null;

    List<String> out = new ArrayList<>();
    for (String part : q.split("&")) {
      if (part.isEmpty()) continue;
      String[] kv = part.split("=", 2);
      String key = urlDecode(kv[0]);
      String value = kv.length == 2 ? urlDecode(kv[1]) : "";

      // Neon/libpq uses channel_binding; PostgreSQL JDBC uses channelBinding.
      if ("channel_binding".equalsIgnoreCase(key)) key = "channelBinding";

      out.add(urlEncode(key) + (kv.length == 2 ? "=" + urlEncode(value) : ""));
    }
    return String.join("&", out);
  }

  private static String urlDecode(String s) {
    return URLDecoder.decode(s, StandardCharsets.UTF_8);
  }

  private static String urlEncode(String s) {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (isUnreserved(ch)) {
        b.append(ch);
      } else {
        byte[] bytes = String.valueOf(ch).getBytes(StandardCharsets.UTF_8);
        for (byte by : bytes) {
          b.append('%');
          String hex = Integer.toHexString(by & 0xFF).toUpperCase();
          if (hex.length() == 1) b.append('0');
          b.append(hex);
        }
      }
    }
    return b.toString();
  }

  private static boolean isUnreserved(char ch) {
    return (ch >= 'a' && ch <= 'z')
        || (ch >= 'A' && ch <= 'Z')
        || (ch >= '0' && ch <= '9')
        || ch == '-'
        || ch == '.'
        || ch == '_'
        || ch == '~';
  }

  public static Connection getConnection() throws SQLException {
    DbInfo info = resolve();
    return DriverManager.getConnection(info.jdbcUrl, info.user, info.password);
  }
}
