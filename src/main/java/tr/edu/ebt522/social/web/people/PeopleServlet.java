package tr.edu.ebt522.social.web.people;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tr.edu.ebt522.social.dao.UserDao;
import tr.edu.ebt522.social.model.UserProfile;

public class PeopleServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("pageTitle", "Kişiler");
    try {
      List<UserProfile> people = UserDao.listAllProfiles();
      request.setAttribute("people", people);
      request.getRequestDispatcher("/WEB-INF/views/people/index.jsp").forward(request, response);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

