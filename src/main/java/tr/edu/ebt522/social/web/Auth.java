package tr.edu.ebt522.social.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tr.edu.ebt522.social.model.User;

public final class Auth {
  private Auth() {}

  public static User getUser(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) return null;
    Object o = session.getAttribute(SessionKeys.AUTH_USER);
    if (!(o instanceof User)) return null;
    return (User) o;
  }

  public static User requireLogin(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    User u = getUser(request);
    if (u != null) return u;
    request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Bu işlem için oturum açmalısınız.");
    response.sendRedirect(request.getContextPath() + "/login");
    return null;
  }

  public static boolean requireAdmin(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    User u = getUser(request);
    if (u != null && u.isAdmin()) return true;
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("text/plain; charset=UTF-8");
    response.getWriter().write("403 - Bu işlem için admin yetkisi gerekir.");
    return false;
  }
}

