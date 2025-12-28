package tr.edu.ebt522.social.web.videos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.VideoCommentDao;
import tr.edu.ebt522.social.dao.VideoDao;
import tr.edu.ebt522.social.model.Comment;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.model.Video;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class VideoServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    long id = Params.longParam(request, "id", -1);
    if (id < 0) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    try {
      Video video = VideoDao.getVideo(id);
      if (video == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      List<Comment> comments = VideoCommentDao.listComments(id);
      request.setAttribute("pageTitle", "Video: " + (video.getTitle() == null ? "" : video.getTitle()));
      request.setAttribute("video", video);
      request.setAttribute("comments", comments);
      request.getRequestDispatcher("/WEB-INF/views/videos/video.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User u = Auth.requireLogin(request, response);
    if (u == null) return;

    long videoId = Params.longParam(request, "video_id", -1);
    String content = Params.str(request, "content");
    if (videoId < 0 || content == null || content.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Yorum boş olamaz.");
      response.sendRedirect(request.getContextPath() + "/video?id=" + videoId);
      return;
    }
    if (content.length() > 2000) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Yorum çok uzun (maks. 2000).");
      response.sendRedirect(request.getContextPath() + "/video?id=" + videoId);
      return;
    }

    try {
      VideoCommentDao.addComment(videoId, u.getId(), content);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Yorum eklendi.");
      response.sendRedirect(request.getContextPath() + "/video?id=" + videoId);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

