package tr.edu.ebt522.social.web;

import javax.servlet.http.HttpServletRequest;

public final class Params {
  private Params() {}

  public static int intParam(HttpServletRequest req, String name, int defaultValue) {
    String raw = req.getParameter(name);
    if (raw == null) return defaultValue;
    try {
      return Integer.parseInt(raw);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public static long longParam(HttpServletRequest req, String name, long defaultValue) {
    String raw = req.getParameter(name);
    if (raw == null) return defaultValue;
    try {
      return Long.parseLong(raw);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public static Long longParamObj(HttpServletRequest req, String name) {
    String raw = req.getParameter(name);
    if (raw == null || raw.trim().isEmpty()) return null;
    try {
      return Long.parseLong(raw.trim());
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public static String str(HttpServletRequest req, String name) {
    String v = req.getParameter(name);
    return v == null ? null : v.trim();
  }
}

