package tr.edu.ebt522.social.web.auth;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.UserDao;
import tr.edu.ebt522.social.util.Passwords;
import tr.edu.ebt522.social.web.SessionKeys;

public class LoginServlet extends HttpServlet {
  private static String p(HttpServletRequest req, String name) {
    String v = req.getParameter(name);
    return v == null ? null : v.trim();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Oturum Açma");
    request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Oturum Açma");
    String login = p(request, "login");
    String password = request.getParameter("password");

    if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
      request.setAttribute("error", "Lütfen kullanıcı adı/e‑posta ve şifre giriniz.");
      request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
      return;
    }

    try {
      UserDao.AuthRecord rec = UserDao.findForLogin(login);
      if (rec == null || !Passwords.verify(password.toCharArray(), rec.passwordStored)) {
        request.setAttribute("error", "Giriş bilgileri hatalı.");
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        return;
      }

      request.getSession().setAttribute(SessionKeys.AUTH_USER, rec.user);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Hoş geldiniz, " + rec.user.getFullName() + "!");
      response.sendRedirect(request.getContextPath() + "/home");
    } catch (SQLException e) {
      request.setAttribute("error", "Giriş sırasında hata oluştu: " + e.getMessage());
      request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }
  }
}

