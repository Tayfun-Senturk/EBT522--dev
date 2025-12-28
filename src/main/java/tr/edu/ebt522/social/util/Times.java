package tr.edu.ebt522.social.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class Times {
  private Times() {}

  private static final DateTimeFormatter SERVER_FMT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

  public static String formatServer(long epochMs) {
    return SERVER_FMT.format(Instant.ofEpochMilli(epochMs));
  }
}

