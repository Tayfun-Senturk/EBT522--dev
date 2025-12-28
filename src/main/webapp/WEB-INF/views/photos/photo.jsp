<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ebt522.social.model.Photo" %>
<%@ page import="tr.edu.ebt522.social.model.PhotoTag" %>
<%@ page import="tr.edu.ebt522.social.model.Comment" %>
<%@ page import="tr.edu.ebt522.social.model.UserProfile" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ page import="tr.edu.ebt522.social.util.Times" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  Photo photo = (Photo) request.getAttribute("photo");
  List<Comment> comments = (List<Comment>) request.getAttribute("comments");
  List<PhotoTag> tags = (List<PhotoTag>) request.getAttribute("tags");
  List<UserProfile> people = (List<UserProfile>) request.getAttribute("people");
  String ctx = request.getContextPath();

  String img = photo.getImagePath();
  String imgUrl = (img == null || img.trim().isEmpty())
      ? (ctx + "/assets/img/photo-default.svg")
      : (img.startsWith("http://") || img.startsWith("https://"))
          ? img
          : (ctx + "/" + img.replaceFirst("^/+", ""));

  if (people == null) people = java.util.List.of();
%>

<div class="card section">
  <h2><%= Html.esc(photo.getTitle()) %></h2>
  <p class="muted">Fotoğraf tarihi: <time data-epoch-ms="<%= photo.getCreatedAtMs() %>"><%= Times.formatServer(photo.getCreatedAtMs()) %></time></p>

  <div style="margin-top: 12px;">
    <img class="bigimg" src="<%= imgUrl %>" alt="Fotoğraf" usemap="#photomap">
    <map name="photomap">
      <% if (tags != null) { %>
        <% for (PhotoTag t : tags) { %>
          <%
            String href = (t.getTaggedUserId() == null) ? null : (ctx + "/person?id=" + t.getTaggedUserId());
            String title = (t.getLabel() == null ? "" : t.getLabel());
          %>
          <% if (href != null) { %>
            <area shape="<%= Html.esc(t.getShape()) %>" coords="<%= Html.esc(t.getCoords()) %>" href="<%= href %>" alt="<%= Html.esc(title) %>" title="<%= Html.esc(title) %>">
          <% } else { %>
            <area shape="<%= Html.esc(t.getShape()) %>" coords="<%= Html.esc(t.getCoords()) %>" alt="<%= Html.esc(title) %>" title="<%= Html.esc(title) %>">
          <% } %>
        <% } %>
      <% } %>
    </map>
    <div class="muted" style="margin-top: 8px;">Etiketli alanlar (varsa) fotoğraf üzerinde tıklanabilir.</div>
  </div>
</div>

<% if (auth != null && auth.isAdmin()) { %>
  <div class="card section" style="margin-top: 14px;">
    <h2>Etiket Ekle (Admin • coords/shape)</h2>
    <p class="muted">Örn: rect=10,10,120,120 • circle=60,60,40 • poly=10,10,50,10,50,50,10,50</p>
    <form method="post" action="<%= ctx %>/photo/tag/add">
      <input type="hidden" name="photo_id" value="<%= photo.getId() %>">
      <div class="grid" style="margin-top: 8px;">
        <div class="col-6">
          <label>Kişi</label>
          <select name="tagged_user_id">
            <option value="">(Sadece bilgi etiketi)</option>
            <% for (UserProfile p : people) { %>
              <option value="<%= p.getId() %>"><%= Html.esc(p.getFullName()) %></option>
            <% } %>
          </select>
        </div>
        <div class="col-6">
          <label>Shape</label>
          <select name="shape" required>
            <option value="rect">rect</option>
            <option value="circle">circle</option>
            <option value="poly">poly</option>
          </select>
        </div>
        <div class="col-12">
          <label>Coords *</label>
          <input class="input" name="coords" placeholder="10,10,120,120" required>
        </div>
        <div class="col-12">
          <label>Etiket Metni</label>
          <input class="input" name="label" placeholder="Örn: Ahmet">
        </div>
      </div>
      <div style="display:flex; justify-content:flex-end; margin-top: 10px;">
        <button class="btn primary" type="submit">Etiket Ekle</button>
      </div>
    </form>

    <h2 style="margin-top: 16px;">Mevcut Etiketler</h2>
    <table class="table">
      <thead><tr><th>ID</th><th>Shape</th><th>Coords</th><th>Kişi</th><th>İşlem</th></tr></thead>
      <tbody>
        <% if (tags == null || tags.isEmpty()) { %>
          <tr><td colspan="5" class="muted">Etiket yok.</td></tr>
        <% } else { %>
          <% for (PhotoTag t : tags) { %>
            <tr>
              <td><%= t.getId() %></td>
              <td><%= Html.esc(t.getShape()) %></td>
              <td><%= Html.esc(t.getCoords()) %></td>
              <td><%= t.getTaggedUserId() == null ? "-" : t.getTaggedUserId() %></td>
              <td>
                <form method="post" action="<%= ctx %>/photo/tag/delete" style="display:inline;">
                  <input type="hidden" name="tag_id" value="<%= t.getId() %>">
                  <input type="hidden" name="photo_id" value="<%= photo.getId() %>">
                  <button class="btn danger" type="submit" onclick="return confirm('Etiket silinsin mi?')">Sil</button>
                </form>
              </td>
            </tr>
          <% } %>
        <% } %>
      </tbody>
    </table>
  </div>
<% } %>

<div class="card section" style="margin-top: 14px;">
  <h2>Yorum Yap</h2>
  <% if (auth == null) { %>
    <div class="flash">Yorum yapmak için <a href="<%= ctx %>/login"><u>oturum açın</u></a>.</div>
  <% } else { %>
    <form method="post" action="<%= ctx %>/photo">
      <input type="hidden" name="photo_id" value="<%= photo.getId() %>">
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
                <a class="btn" href="<%= ctx %>/photo/comment/edit?comment_id=<%= c.getId() %>">Düzenle</a>
                <form method="post" action="<%= ctx %>/photo/comment/delete" style="display:inline;">
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
