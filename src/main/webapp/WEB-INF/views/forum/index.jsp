<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ebt522.social.model.ForumPost" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ page import="tr.edu.ebt522.social.util.Times" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  List<ForumPost> posts = (List<ForumPost>) request.getAttribute("posts");
  int page = (Integer) request.getAttribute("page");
  int totalPages = (Integer) request.getAttribute("totalPages");
%>

<div class="card section">
  <h2>Forum</h2>
  <p class="muted">Ortak mesajlaşma alanı. Mesajlar tarih‑saat formatında listelenir.</p>

  <% if (auth == null) { %>
    <div class="flash">Mesaj yazmak için <a href="<%= request.getContextPath() %>/login"><u>oturum açın</u></a>.</div>
  <% } else { %>
    <form method="post" action="<%= request.getContextPath() %>/forum" style="margin-top: 12px;">
      <label>Mesajınız</label>
      <textarea name="content" placeholder="Mesaj yaz..."></textarea>
      <div style="display:flex; justify-content:flex-end; margin-top: 10px;">
        <button class="btn primary" type="submit">Mesajı Gönder</button>
      </div>
    </form>
  <% } %>
</div>

<div class="card section" style="margin-top: 14px;">
  <h2>Mesajlar</h2>

  <table class="table">
    <thead>
      <tr>
        <th style="width: 170px;">Tarih‑Saat</th>
        <th style="width: 180px;">Gönderen</th>
        <th>Mesaj</th>
        <th style="width: 150px;">İşlemler</th>
      </tr>
    </thead>
    <tbody>
      <% if (posts == null || posts.isEmpty()) { %>
        <tr><td colspan="4" class="muted">Henüz mesaj yok.</td></tr>
      <% } else { %>
        <% for (ForumPost post : posts) { %>
          <tr>
            <td><time data-epoch-ms="<%= post.getCreatedAtMs() %>"><%= Times.formatServer(post.getCreatedAtMs()) %></time></td>
            <td><%= Html.esc(post.getAuthorName()) %></td>
            <td><%= Html.escNl2Br(post.getContent()) %></td>
            <td>
              <% if (auth != null && auth.isAdmin()) { %>
                <a class="btn" href="<%= request.getContextPath() %>/forum/edit?id=<%= post.getId() %>">Düzenle</a>
                <form method="post" action="<%= request.getContextPath() %>/forum/delete" style="display:inline;">
                  <input type="hidden" name="id" value="<%= post.getId() %>">
                  <button class="btn danger" type="submit" onclick="return confirm('Silinsin mi?')">Sil</button>
                </form>
              <% } else { %>
                <span class="muted">—</span>
              <% } %>
            </td>
          </tr>
        <% } %>
      <% } %>
    </tbody>
  </table>

  <div style="display:flex; justify-content:space-between; align-items:center; margin-top: 12px; gap: 10px; flex-wrap:wrap;">
    <div class="muted">Sayfa: <%= page %> / <%= totalPages %></div>
    <div style="display:flex; gap:8px; flex-wrap:wrap;">
      <% if (page > 1) { %>
        <a class="btn" href="<%= request.getContextPath() %>/forum?page=<%= page - 1 %>">Önceki</a>
      <% } %>
      <% if (page < totalPages) { %>
        <a class="btn" href="<%= request.getContextPath() %>/forum?page=<%= page + 1 %>">Sonraki</a>
      <% } %>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
