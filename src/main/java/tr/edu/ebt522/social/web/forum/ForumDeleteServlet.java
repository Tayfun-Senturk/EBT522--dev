package tr.edu.ebt522.social.web.forum;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.ForumDao;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class ForumDeleteServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;

    long id = Params.longParam(request, "id", -1);
    if (id < 0) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Geçersiz istek.");
      response.sendRedirect(request.getContextPath() + "/forum");
      return;
    }

    try {
      ForumDao.deletePost(id);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Forum mesajı silindi.");
      response.sendRedirect(request.getContextPath() + "/forum");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

