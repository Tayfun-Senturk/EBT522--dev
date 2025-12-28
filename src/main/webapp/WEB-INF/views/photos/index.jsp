<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ebt522.social.model.Photo" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ page import="tr.edu.ebt522.social.util.Times" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  List<Photo> photos = (List<Photo>) request.getAttribute("photos");
  String ctx = request.getContextPath();
%>

<div class="card section">
  <h2>Fotoğraflar</h2>
  <p class="muted">Eklenen tüm fotoğrafların thumbnail görselleri ve fotoğraf sayfasına bağlantıları.</p>
  <% if (auth != null && auth.isAdmin()) { %>
    <div style="margin-top: 10px;">
      <a class="btn primary" href="<%= ctx %>/photos/add">Fotoğraf Ekle (Admin)</a>
    </div>
  <% } %>
</div>

<div class="grid" style="margin-top: 14px;">
  <% if (photos == null || photos.isEmpty()) { %>
    <div class="card section col-12 muted">Henüz fotoğraf yok.</div>
  <% } else { %>
    <% for (Photo p : photos) { %>
      <div class="card section col-6" style="display:flex; gap:12px; align-items:center;">
        <%
          String thumb = p.getThumbPath();
          String thumbUrl = (thumb == null || thumb.trim().isEmpty())
              ? (ctx + "/assets/img/photo-default.svg")
              : (thumb.startsWith("http://") || thumb.startsWith("https://"))
                  ? thumb
                  : (ctx + "/" + thumb.replaceFirst("^/+", ""));
        %>
        <img class="thumb" src="<%= thumbUrl %>" alt="Thumbnail">
        <div>
          <div style="font-weight:800;"><%= Html.esc(p.getTitle()) %></div>
          <div class="muted"><time data-epoch-ms="<%= p.getCreatedAtMs() %>"><%= Times.formatServer(p.getCreatedAtMs()) %></time></div>
          <div style="margin-top:10px;">
            <a class="btn primary" href="<%= ctx %>/photo?id=<%= p.getId() %>">Fotoğraf Sayfası</a>
          </div>
        </div>
      </div>
    <% } %>
  <% } %>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
