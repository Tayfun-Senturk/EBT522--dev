package tr.edu.ebt522.social.web.photos;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.PhotoDao;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class PhotoAddServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;
    request.setAttribute("pageTitle", "Fotoğraf Ekle");
    request.getRequestDispatcher("/WEB-INF/views/photos/add.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;
    User admin = Auth.getUser(request);
    String title = Params.str(request, "title");
    String imagePath = Params.str(request, "image_path");
    String thumbPath = Params.str(request, "thumb_path");
    if (imagePath == null || imagePath.isEmpty() || thumbPath == null || thumbPath.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Resim yolu ve thumbnail yolu zorunludur.");
      response.sendRedirect(request.getContextPath() + "/photos/add");
      return;
    }
    try {
      PhotoDao.addPhoto(title, imagePath, thumbPath, admin == null ? null : admin.getId());
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Fotoğraf eklendi.");
      response.sendRedirect(request.getContextPath() + "/photos");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

