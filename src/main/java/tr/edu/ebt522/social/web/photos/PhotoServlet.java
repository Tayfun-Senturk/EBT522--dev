package tr.edu.ebt522.social.web.photos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.PhotoCommentDao;
import tr.edu.ebt522.social.dao.PhotoDao;
import tr.edu.ebt522.social.dao.PhotoTagDao;
import tr.edu.ebt522.social.dao.UserDao;
import tr.edu.ebt522.social.model.Comment;
import tr.edu.ebt522.social.model.Photo;
import tr.edu.ebt522.social.model.PhotoTag;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.model.UserProfile;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class PhotoServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    long id = Params.longParam(request, "id", -1);
    if (id < 0) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    try {
      Photo photo = PhotoDao.getPhoto(id);
      if (photo == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      List<Comment> comments = PhotoCommentDao.listComments(id);
      List<PhotoTag> tags = PhotoTagDao.listTags(id);
      User auth = Auth.getUser(request);
      if (auth != null && auth.isAdmin()) {
        List<UserProfile> people = UserDao.listAllProfiles();
        request.setAttribute("people", people);
      }
      request.setAttribute("pageTitle", "Fotoğraf: " + (photo.getTitle() == null ? "" : photo.getTitle()));
      request.setAttribute("photo", photo);
      request.setAttribute("comments", comments);
      request.setAttribute("tags", tags);
      request.getRequestDispatcher("/WEB-INF/views/photos/photo.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User u = Auth.requireLogin(request, response);
    if (u == null) return;

    long photoId = Params.longParam(request, "photo_id", -1);
    String content = Params.str(request, "content");
    if (photoId < 0 || content == null || content.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Yorum boş olamaz.");
      response.sendRedirect(request.getContextPath() + "/photo?id=" + photoId);
      return;
    }
    if (content.length() > 2000) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Yorum çok uzun (maks. 2000).");
      response.sendRedirect(request.getContextPath() + "/photo?id=" + photoId);
      return;
    }

    try {
      PhotoCommentDao.addComment(photoId, u.getId(), content);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Yorum eklendi.");
      response.sendRedirect(request.getContextPath() + "/photo?id=" + photoId);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}
