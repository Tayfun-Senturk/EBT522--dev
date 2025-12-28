<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="tr.edu.ebt522.social.model.Comment" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  Comment c = (Comment) request.getAttribute("comment");
  String ctx = request.getContextPath();
%>

<div class="card section">
  <h2>Video Yorumu Düzenle</h2>
  <p class="muted">Admin yetkisiyle düzenleme.</p>

  <form method="post" action="<%= ctx %>/video/comment/edit">
    <input type="hidden" name="comment_id" value="<%= c.getId() %>">
    <label>Yorum</label>
    <textarea name="content"><%= Html.esc(c.getContent()) %></textarea>
    <div style="display:flex; gap:10px; margin-top: 12px;">
      <button class="btn primary" type="submit">Kaydet</button>
      <a class="btn" href="<%= ctx %>/video?id=<%= c.getTargetId() %>">Vazgeç</a>
    </div>
  </form>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
