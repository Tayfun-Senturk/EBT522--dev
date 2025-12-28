<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ebt522.social.model.UserProfile" %>
<%@ page import="tr.edu.ebt522.social.model.Comment" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ page import="tr.edu.ebt522.social.util.Times" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  UserProfile person = (UserProfile) request.getAttribute("person");
  List<Comment> comments = (List<Comment>) request.getAttribute("comments");
  String ctx = request.getContextPath();
  String avatar = person.getAvatarPath();
  String avatarUrl = (avatar == null || avatar.trim().isEmpty())
      ? (ctx + "/assets/img/avatar-default.svg")
      : (avatar.startsWith("http://") || avatar.startsWith("https://"))
          ? avatar
          : (ctx + "/" + avatar.replaceFirst("^/+", ""));
%>

<div class="card section">
  <h2><%= Html.esc(person.getFullName()) %></h2>
  <p class="muted">@<%= Html.esc(person.getUsername()) %> • Rol: <%= Html.esc(person.getRole()) %></p>

  <div class="grid" style="margin-top: 10px;">
    <div class="col-6">
      <img class="bigimg" src="<%= avatarUrl %>" alt="Profil Fotoğrafı">
    </div>
    <div class="col-6">
      <table class="table">
        <tbody>
          <tr><th>Şehir</th><td><%= Html.esc(person.getCity()) %></td></tr>
          <tr><th>Telefon</th><td><%= Html.esc(person.getPhone()) %></td></tr>
          <tr><th>E‑posta</th><td><%= Html.esc(person.getEmail()) %></td></tr>
          <tr><th>Okul</th><td><%= Html.esc(person.getCurrentSchool()) %></td></tr>
          <tr><th>İş</th><td><%= Html.esc(person.getCurrentJob()) %></td></tr>
          <tr><th>Web</th><td><%= Html.esc(person.getWebUrl()) %></td></tr>
          <tr><th>Facebook</th><td><%= Html.esc(person.getFacebookId()) %></td></tr>
          <tr><th>Twitter</th><td><%= Html.esc(person.getTwitterHandle()) %></td></tr>
        </tbody>
      </table>
    </div>
    <div class="col-12">
      <div class="muted" style="margin-top: 10px;">
        <strong>Adres:</strong> <%= Html.esc(person.getAddress()) %>
      </div>
      <div class="muted" style="margin-top: 8px;">
        <strong>Hakkımda:</strong> <%= Html.esc(person.getAbout()) %>
      </div>
    </div>
  </div>
</div>

<div class="card section" style="margin-top: 14px;">
  <h2>Yorum Yap</h2>
  <% if (auth == null) { %>
    <div class="flash">Yorum yapmak için <a href="<%= ctx %>/login"><u>oturum açın</u></a>.</div>
  <% } else { %>
    <form method="post" action="<%= ctx %>/person">
      <input type="hidden" name="person_id" value="<%= person.getId() %>">
      <label>Yorum</label>
      <textarea name="content" placeholder="Yorum yaz..."></textarea>
      <div style="display:flex; justify-content:flex-end; margin-top: 10px;">
        <button class="btn primary" type="submit">Yorumu Gönder</button>
      </div>
    </form>
  <% } %>
</div>

<div class="card section" style="margin-top: 14px;">
  <h2>Yorumlar</h2>
  <table class="table">
    <thead>
      <tr>
        <th style="width: 170px;">Tarih‑Saat</th>
        <th style="width: 180px;">Yorumcu</th>
        <th>Yorum</th>
        <th style="width: 170px;">İşlemler</th>
      </tr>
    </thead>
    <tbody>
      <% if (comments == null || comments.isEmpty()) { %>
        <tr><td colspan="4" class="muted">Henüz yorum yok.</td></tr>
      <% } else { %>
        <% for (Comment c : comments) { %>
          <tr>
            <td><time data-epoch-ms="<%= c.getCreatedAtMs() %>"><%= Times.formatServer(c.getCreatedAtMs()) %></time></td>
            <td><%= Html.esc(c.getAuthorName()) %></td>
            <td><%= Html.escNl2Br(c.getContent()) %></td>
            <td>
              <% if (auth != null && auth.isAdmin()) { %>
                <a class="btn" href="<%= ctx %>/person/comment/edit?comment_id=<%= c.getId() %>">Düzenle</a>
                <form method="post" action="<%= ctx %>/person/comment/delete" style="display:inline;">
                  <input type="hidden" name="comment_id" value="<%= c.getId() %>">
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
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
