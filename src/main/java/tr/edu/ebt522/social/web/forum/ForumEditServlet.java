package tr.edu.ebt522.social.web.forum;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.ForumDao;
import tr.edu.ebt522.social.model.ForumPost;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class ForumEditServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;
    request.setAttribute("pageTitle", "Forum Mesajı Düzenle");

    long id = Params.longParam(request, "id", -1);
    if (id < 0) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    try {
      ForumPost post = ForumDao.getPost(id);
      if (post == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      request.setAttribute("post", post);
      request.getRequestDispatcher("/WEB-INF/views/forum/edit.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;

    long id = Params.longParam(request, "id", -1);
    String content = Params.str(request, "content");
    if (id < 0 || content == null || content.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Geçersiz istek.");
      response.sendRedirect(request.getContextPath() + "/forum");
      return;
    }
    try {
      ForumDao.updatePost(id, content);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Forum mesajı güncellendi.");
      response.sendRedirect(request.getContextPath() + "/forum");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

