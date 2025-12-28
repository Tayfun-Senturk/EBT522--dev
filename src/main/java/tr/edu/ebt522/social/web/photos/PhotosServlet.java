package tr.edu.ebt522.social.web.photos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.PhotoDao;
import tr.edu.ebt522.social.model.Photo;

public class PhotosServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Fotoğraflar");
    try {
      List<Photo> photos = PhotoDao.listPhotos();
      request.setAttribute("photos", photos);
      request.getRequestDispatcher("/WEB-INF/views/photos/index.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

