package tr.edu.ebt522.social.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class Passwords {
  private Passwords() {}

  private static final String ALGO = "PBKDF2WithHmacSHA256";
  private static final int DEFAULT_ITERATIONS = 310_000;
  private static final int SALT_BYTES = 16;
  private static final int KEY_BITS = 256;

  public static final class Hash {
    public final String algo;
    public final int iterations;
    public final String saltB64;
    public final String hashB64;

    public Hash(String algo, int iterations, String saltB64, String hashB64) {
      this.algo = algo;
      this.iterations = iterations;
      this.saltB64 = saltB64;
      this.hashB64 = hashB64;
    }

    public String asStoredString() {
      return algo + ":" + iterations + ":" + saltB64 + ":" + hashB64;
    }
  }

  public static Hash hash(char[] password) {
    byte[] salt = new byte[SALT_BYTES];
    new SecureRandom().nextBytes(salt);
    return hashWithSalt(password, salt, DEFAULT_ITERATIONS);
  }

  public static Hash hashWithSalt(char[] password, byte[] salt, int iterations) {
    try {
      PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, KEY_BITS);
      SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGO);
      byte[] key = skf.generateSecret(spec).getEncoded();
      String saltB64 = Base64.getEncoder().encodeToString(salt);
      String hashB64 = Base64.getEncoder().encodeToString(key);
      return new Hash(ALGO, iterations, saltB64, hashB64);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new IllegalStateException("Şifre hashleme hatası: " + e.getMessage(), e);
    }
  }

  public static boolean verify(char[] password, String stored) {
    if (stored == null) return false;
    String[] parts = stored.split(":");
    if (parts.length != 4) return false;

    String algo = parts[0];
    int iterations;
    try {
      iterations = Integer.parseInt(parts[1]);
    } catch (NumberFormatException e) {
      return false;
    }
    if (!ALGO.equals(algo)) return false;

    byte[] salt;
    byte[] expected;
    try {
      salt = Base64.getDecoder().decode(parts[2]);
      expected = Base64.getDecoder().decode(parts[3]);
    } catch (IllegalArgumentException e) {
      return false;
    }

    Hash computed = hashWithSalt(password, salt, iterations);
    byte[] got = Base64.getDecoder().decode(computed.hashB64);
    if (got.length != expected.length) return false;
    int diff = 0;
    for (int i = 0; i < got.length; i++) {
      diff |= (got[i] ^ expected[i]);
    }
    return diff == 0;
  }
}

