package tr.edu.ebt522.social.util;

public final class Html {
  private Html() {}

  public static String esc(String s) {
    if (s == null) return "";
    StringBuilder out = new StringBuilder(s.length() + 16);
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '&':
          out.append("&amp;");
          break;
        case '<':
          out.append("&lt;");
          break;
        case '>':
          out.append("&gt;");
          break;
        case '"':
          out.append("&quot;");
          break;
        case '\'':
          out.append("&#x27;");
          break;
        default:
          out.append(c);
      }
    }
    return out.toString();
  }

  public static String escNl2Br(String s) {
    return esc(s).replace("\r\n", "\n").replace("\n", "<br>");
  }
}
