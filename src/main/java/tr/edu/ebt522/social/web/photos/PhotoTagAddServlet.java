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

public class PhotoTagAddServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!Auth.requireAdmin(request, response)) return;

    long photoId = Params.longParam(request, "photo_id", -1);
    Long taggedUserId = Params.longParamObj(request, "tagged_user_id");
    String shape = Params.str(request, "shape");
    String coords = Params.str(request, "coords");
    String label = Params.str(request, "label");

    if (photoId < 0 || shape == null || coords == null || coords.isEmpty()) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Geçersiz etiket bilgisi.");
      response.sendRedirect(request.getContextPath() + "/photo?id=" + photoId);
      return;
    }
    String shapeNorm = shape.toLowerCase();
    if (!("rect".equals(shapeNorm) || "circle".equals(shapeNorm) || "poly".equals(shapeNorm))) {
      request.getSession().setAttribute(SessionKeys.FLASH_ERROR, "Shape rect/circle/poly olmalıdır.");
      response.sendRedirect(request.getContextPath() + "/photo?id=" + photoId);
      return;
    }

    try {
      PhotoTagDao.addTag(photoId, taggedUserId, shapeNorm, coords, label);
      request.getSession().setAttribute(SessionKeys.FLASH_OK, "Etiket eklendi.");
      response.sendRedirect(request.getContextPath() + "/photo?id=" + photoId);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}

