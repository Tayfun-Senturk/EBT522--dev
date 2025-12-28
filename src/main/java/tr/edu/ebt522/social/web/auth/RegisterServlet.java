package tr.edu.ebt522.social.web.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.UserDao;
import tr.edu.ebt522.social.util.Passwords;
import tr.edu.ebt522.social.web.SessionKeys;

public class RegisterServlet extends HttpServlet {
  private static String p(HttpServletRequest req, String name) {
    String v = req.getParameter(name);
    return v == null ? null : v.trim();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Kişi Kayıt");
    request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Kişi Kayıt");

    String firstName = p(request, "first_name");
    String lastName = p(request, "last_name");
    String username = p(request, "username");
    String email = p(request, "email");
    String password = request.getParameter("password");
    String password2 = request.getParameter("password2");

    String gender = p(request, "gender");
    String[] hobbiesArr = request.getParameterValues("hobbies");
    List<String> hobbies = hobbiesArr == null ? List.of() : Arrays.asList(hobbiesArr);
    String city = p(request, "city");

    LocalDate birthDate = null;
    String birthRaw = p(request, "birth_date");
    if (birthRaw != null && !birthRaw.isEmpty()) {
      try {
        birthDate = LocalDate.parse(birthRaw);
      } catch (DateTimeParseException e) {
        request.setAttribute("error", "Doğum tarihi formatı geçersiz.");
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        return;
      }
    }

    String phone = p(request, "phone");
    String address = p(request, "address");
    String about = p(request, "about");
    String currentSchool = p(request, "current_school");
    String currentJob = p(request, "current_job");
    String webUrl = p(request, "web_url");
    String facebookId = p(request, "facebook_id");
    String twitterHandle = p(request, "twitter_handle");

    if (firstName == null
        || firstName.isEmpty()
        || lastName == null
        || lastName.isEmpty()
        || username == null
        || username.isEmpty()
        || email == null
        || email.isEmpty()
        || password == null
        || password.isEmpty()
        || password2 == null
        || password2.isEmpty()) {
      request.setAttribute("error", "Lütfen zorunlu alanları doldurunuz.");
      request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
      return;
    }
    if (username.length() < 3) {
      request.setAttribute("error", "Kullanıcı adı en az 3 karakter olmalıdır.");
      request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
      return;
    }
    if (password.length() < 6) {
      request.setAttribute("error", "Şifre en az 6 karakter olmalıdır.");
      request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
      return;
    }
    if (!password.equals(password2)) {
      request.setAttribute("error", "Şifreler eşleşmiyor.");
      request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
      return;
    }

    try {
      if (UserDao.usernameExists(username)) {
        request.setAttribute("error", "Bu kullanıcı adı zaten kullanılıyor.");
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        return;
      }
      if (UserDao.emailExists(email)) {
        request.setAttribute("error", "Bu e‑posta zaten kullanılıyor.");
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        return;
      }

      Passwords.Hash h = Passwords.hash(password.toCharArray());
      UserDao.createUser(
          username,
          email,
          h.asStoredString(),
          h.saltB64,
          h.iterations,
          firstName,
          lastName,
          gender,
          hobbies,
          city,
          birthDate,
          phone,
          address,
          about,
          currentSchool,
          currentJob,
          webUrl,
          facebookId,
          twitterHandle,
          null);

      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Kayıt başarılı. Şimdi oturum açabilirsiniz.");
      response.sendRedirect(request.getContextPath() + "/login");
    } catch (SQLException e) {
      request.setAttribute("error", "Kayıt sırasında hata oluştu: " + e.getMessage());
      request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }
  }
}

