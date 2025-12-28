package tr.edu.ebt522.social.model;

import java.time.LocalDate;
import java.util.List;

public class UserProfile {
  private final long id;
  private final String username;
  private final String email;
  private final String firstName;
  private final String lastName;
  private final String role;

  private final String gender;
  private final List<String> hobbies;
  private final String city;
  private final LocalDate birthDate;

  private final String phone;
  private final String address;
  private final String about;

  private final String currentSchool;
  private final String currentJob;
  private final String webUrl;
  private final String facebookId;
  private final String twitterHandle;

  private final String avatarPath;

  public UserProfile(
      long id,
      String username,
      String email,
      String firstName,
      String lastName,
      String role,
      String gender,
      List<String> hobbies,
      String city,
      LocalDate birthDate,
      String phone,
      String address,
      String about,
      String currentSchool,
      String currentJob,
      String webUrl,
      String facebookId,
      String twitterHandle,
      String avatarPath) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.gender = gender;
    this.hobbies = hobbies;
    this.city = city;
    this.birthDate = birthDate;
    this.phone = phone;
    this.address = address;
    this.about = about;
    this.currentSchool = currentSchool;
    this.currentJob = currentJob;
    this.webUrl = webUrl;
    this.facebookId = facebookId;
    this.twitterHandle = twitterHandle;
    this.avatarPath = avatarPath;
  }

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
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

  public String getGender() {
    return gender;
  }

  public List<String> getHobbies() {
    return hobbies;
  }

  public String getCity() {
    return city;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public String getAddress() {
    return address;
  }

  public String getAbout() {
    return about;
  }

  public String getCurrentSchool() {
    return currentSchool;
  }

  public String getCurrentJob() {
    return currentJob;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public String getFacebookId() {
    return facebookId;
  }

  public String getTwitterHandle() {
    return twitterHandle;
  }

  public String getAvatarPath() {
    return avatarPath;
  }

  public String getFullName() {
    return (firstName + " " + lastName).trim();
  }

  public boolean isAdmin() {
    return "ADMIN".equalsIgnoreCase(role);
  }
}

