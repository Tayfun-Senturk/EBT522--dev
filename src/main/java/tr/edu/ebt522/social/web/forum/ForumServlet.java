package tr.edu.ebt522.social.web.forum;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.ForumDao;
import tr.edu.ebt522.social.model.ForumPost;
import tr.edu.ebt522.social.model.User;
import tr.edu.ebt522.social.web.Auth;
import tr.edu.ebt522.social.web.Params;
import tr.edu.ebt522.social.web.SessionKeys;

public class ForumServlet extends HttpServlet {
  private static final int PAGE_SIZE = 10;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Forum");
    int page = Params.intParam(request, "page", 1);
    if (page < 1) page = 1;

    try {
      int total = ForumDao.countPosts();
      int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);
      if (totalPages == 0) totalPages = 1;
      if (page > totalPages) page = totalPages;

      List<ForumPost> posts = ForumDao.listPosts(page, PAGE_SIZE);
      request.setAttribute("posts", posts);
      request.setAttribute("page", page);
      request.setAttribute("totalPages", totalPages);
      request.getRequestDispatcher("/WEB-INF/views/forum/index.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User user = Auth.requireLogin(request, response);
    if (user == null) return;

    String content = Params.str(request, "content");
    if (content == null || content.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Mesaj boş olamaz.");
      response.sendRedirect(request.getContextPath() + "/forum");
      return;
    }
    if (content.length() > 2000) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Mesaj çok uzun (maks. 2000).");
      response.sendRedirect(request.getContextPath() + "/forum");
      return;
    }

    try {
      ForumDao.createPost(user.getId(), content);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Mesajınız foruma eklendi.");
      response.sendRedirect(request.getContextPath() + "/forum?page=1");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

