<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ebt522.social.model.Video" %>
<%@ page import="tr.edu.ebt522.social.model.Comment" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ page import="tr.edu.ebt522.social.util.Times" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  Video video = (Video) request.getAttribute("video");
  List<Comment> comments = (List<Comment>) request.getAttribute("comments");
  String ctx = request.getContextPath();
  String youtubeId = video.getYoutubeId();
%>

<div class="card section">
  <h2><%= Html.esc(video.getTitle()) %></h2>
  <p class="muted">Video tarihi: <time data-epoch-ms="<%= video.getCreatedAtMs() %>"><%= Times.formatServer(video.getCreatedAtMs()) %></time></p>

  <div style="margin-top: 12px;">
    <div style="position:relative; width:100%; aspect-ratio:16/9; border-radius: 18px; overflow:hidden; border:1px solid rgba(255,255,255,0.12);">
      <iframe
        title="YouTube"
        src="https://www.youtube.com/embed/<%= Html.esc(youtubeId) %>?autoplay=1&mute=1&rel=0"
        style="position:absolute; inset:0; width:100%; height:100%; border:0;"
        allow="autoplay; encrypted-media; picture-in-picture"
        allowfullscreen></iframe>
    </div>
    <div class="muted" style="margin-top: 8px;">Not: Otomatik oynatma için video sessiz başlatılır (mute=1).</div>
  </div>
</div>

<div class="card section" style="margin-top: 14px;">
  <h2>Yorum Yap</h2>
  <% if (auth == null) { %>
    <div class="flash">Yorum yapmak için <a href="<%= ctx %>/login"><u>oturum açın</u></a>.</div>
  <% } else { %>
    <form method="post" action="<%= ctx %>/video">
      <input type="hidden" name="video_id" value="<%= video.getId() %>">
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
                <a class="btn" href="<%= ctx %>/video/comment/edit?comment_id=<%= c.getId() %>">Düzenle</a>
                <form method="post" action="<%= ctx %>/video/comment/delete" style="display:inline;">
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
