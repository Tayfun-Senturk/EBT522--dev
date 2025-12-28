package tr.edu.ebt522.social.web.videos;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.VideoDao;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class VideoAddServlet extends HttpServlet {
  private static boolean isLikelyYoutubeId(String s) {
    if (s == null) return false;
    String id = s.trim();
    if (id.length() < 6 || id.length() > 20) return false;
    for (int i = 0; i < id.length(); i++) {
      char c = id.charAt(i);
      boolean ok = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_' || c == '-';
      if (!ok) return false;
    }
    return true;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;
    request.setAttribute("pageTitle", "Video Ekle");
    request.getRequestDispatcher("/WEB-INF/views/videos/add.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;
    User admin = Auth.getUser(request);

    String title = Params.str(request, "title");
    String youtubeId = Params.str(request, "youtube_id");
    if (!isLikelyYoutubeId(youtubeId)) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Geçerli bir YouTube video ID giriniz.");
      response.sendRedirect(request.getContextPath() + "/videos/add");
      return;
    }
    String thumb = "https://img.youtube.com/vi/" + youtubeId + "/hqdefault.jpg";

    try {
      VideoDao.addVideo(title, youtubeId, thumb, admin == null ? null : admin.getId());
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Video eklendi.");
      response.sendRedirect(request.getContextPath() + "/videos");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

