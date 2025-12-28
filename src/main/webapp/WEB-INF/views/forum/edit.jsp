<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="tr.edu.ebt522.social.model.ForumPost" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  ForumPost post = (ForumPost) request.getAttribute("post");
%>

<div class="card section">
  <h2>Forum Mesajı Düzenle</h2>
  <p class="muted">Admin yetkisiyle düzenleme.</p>

  <form method="post" action="<%= request.getContextPath() %>/forum/edit">
    <input type="hidden" name="id" value="<%= post.getId() %>">
    <label>Mesaj</label>
    <textarea name="content"><%= Html.esc(post.getContent()) %></textarea>
    <div style="display:flex; gap:10px; margin-top: 12px;">
      <button class="btn primary" type="submit">Kaydet</button>
      <a class="btn" href="<%= request.getContextPath() %>/forum">Vazgeç</a>
    </div>
  </form>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
