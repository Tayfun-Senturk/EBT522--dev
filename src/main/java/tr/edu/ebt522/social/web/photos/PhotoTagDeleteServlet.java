package tr.edu.ebt522.social.web.photos;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.PhotoTagDao;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class PhotoTagDeleteServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;

    long tagId = Params.longParam(request, "tag_id", -1);
    long photoId = Params.longParam(request, "photo_id", -1);
    if (tagId < 0 || photoId < 0) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Geçersiz istek.");
      response.sendRedirect(request.getContextPath() + "/photos");
      return;
    }
    try {
      PhotoTagDao.deleteTag(tagId);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Etiket silindi.");
      response.sendRedirect(request.getContextPath() + "/photo?id=" + photoId);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

