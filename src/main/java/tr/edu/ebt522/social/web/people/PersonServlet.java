package tr.edu.ebt522.social.web.people;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.PersonCommentDao;
import tr.edu.ebt522.social.dao.UserDao;
import tr.edu.ebt522.social.model.Comment;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.model.UserProfile;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class PersonServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    long id = Params.longParam(request, "id", -1);
    if (id < 0) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    try {
      UserProfile person = UserDao.getProfileById(id);
      if (person == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      List<Comment> comments = PersonCommentDao.listComments(id);
      request.setAttribute("pageTitle", "Kişi: " + person.getFullName());
      request.setAttribute("person", person);
      request.setAttribute("comments", comments);
      request.getRequestDispatcher("/WEB-INF/views/people/person.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User u = Auth.requireLogin(request, response);
    if (u == null) return;

    long personId = Params.longParam(request, "person_id", -1);
    String content = Params.str(request, "content");
    if (personId < 0 || content == null || content.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Yorum boş olamaz.");
      response.sendRedirect(request.getContextPath() + "/person?id=" + personId);
      return;
    }
    if (content.length() > 2000) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Yorum çok uzun (maks. 2000).");
      response.sendRedirect(request.getContextPath() + "/person?id=" + personId);
      return;
    }

    try {
      PersonCommentDao.addComment(personId, u.getId(), content);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Yorum eklendi.");
      response.sendRedirect(request.getContextPath() + "/person?id=" + personId);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

