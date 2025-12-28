package tr.edu.ebt522.social.web.activity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.UserDao;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.model.UserProfile;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class ActivityServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Kim, Nerede, Ne Yapıyor?");
    try {
      List<UserProfile> people = UserDao.listAllProfiles();
      request.setAttribute("people", people);
      request.getRequestDispatcher("/WEB-INF/views/activity/index.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;
    User admin = Auth.getUser(request);

    String action = Params.str(request, "action");
    long userId = Params.longParam(request, "user_id", -1);
    if (action == null || userId < 0) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Geçersiz istek.");
      response.sendRedirect(request.getContextPath() + "/activity");
      return;
    }

    if (admin != null && admin.getId() == userId) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Kendi hesabınıza bu işlem uygulanamaz.");
      response.sendRedirect(request.getContextPath() + "/activity");
      return;
    }

    try {
      switch (action) {
        case "delete_user":
          UserDao.deleteUser(userId);
          request.getSession().setAttribute(SessionKeys.FLASH_OK, "Kullanıcı silindi.");
          break;
        case "make_admin":
          UserDao.setRole(userId, "ADMIN");
          request.getSession().setAttribute(SessionKeys.FLASH_OK, "Kullanıcı admin yapıldı.");
          break;
        case "make_user":
          UserDao.setRole(userId, "USER");
          request.getSession().setAttribute(SessionKeys.FLASH_OK, "Kullanıcı normal kullanıcı yapıldı.");
          break;
        default:
          request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Bilinmeyen işlem.");
          break;
      }
      response.sendRedirect(request.getContextPath() + "/activity");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

