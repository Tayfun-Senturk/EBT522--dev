package tr.edu.ebt522.social.model;

public class User {
  private final long id;
  private final String username;
  private final String firstName;
  private final String lastName;
  private final String role;

  public User(long id, String username, String firstName, String lastName, String role) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
  }

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getRole() {
    return role;
  }

  public String getFullName() {
    return (firstName + " " + lastName).trim();
  }

  public boolean isAdmin() {
    return "ADMIN".equalsIgnoreCase(role);
  }
}

