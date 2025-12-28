package tr.edu.ebt522.social.web.people;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.PersonCommentDao;
import tr.edu.ebt522.social.model.Comment;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class PersonCommentEditServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;
    long commentId = Params.longParam(request, "comment_id", -1);
    if (commentId < 0) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    try {
      Comment c = PersonCommentDao.getComment(commentId);
      if (c == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      request.setAttribute("pageTitle", "Kişi Yorumu Düzenle");
      request.setAttribute("comment", c);
      request.getRequestDispatcher("/WEB-INF/views/people/comment_edit.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;

    long commentId = Params.longParam(request, "comment_id", -1);
    String content = Params.str(request, "content");
    if (commentId < 0 || content == null || content.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Geçersiz istek.");
      response.sendRedirect(request.getContextPath() + "/people");
      return;
    }
    try {
      Comment c = PersonCommentDao.getComment(commentId);
      if (c == null) {
        request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Yorum bulunamadı.");
        response.sendRedirect(request.getContextPath() + "/people");
        return;
      }
      PersonCommentDao.updateComment(commentId, content);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Yorum güncellendi.");
      response.sendRedirect(request.getContextPath() + "/person?id=" + c.getTargetId());
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

