package tr.edu.ebt522.social.web.videos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.VideoDao;
import tr.edu.ebt522.social.model.Video;

public class VideosServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Videolar");
    try {
      List<Video> videos = VideoDao.listVideos();
      request.setAttribute("videos", videos);
      request.getRequestDispatcher("/WEB-INF/views/videos/index.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

