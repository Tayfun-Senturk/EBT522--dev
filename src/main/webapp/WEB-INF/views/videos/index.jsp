<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ebt522.social.model.Video" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ page import="tr.edu.ebt522.social.util.Times" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  List<Video> videos = (List<Video>) request.getAttribute("videos");
  String ctx = request.getContextPath();
%>

<div class="card section">
  <h2>Videolar</h2>
  <p class="muted">Eklenen tüm videoların thumbnail görselleri ve video sayfasına bağlantıları.</p>
  <% if (auth != null && auth.isAdmin()) { %>
    <div style="margin-top: 10px;">
      <a class="btn primary" href="<%= ctx %>/videos/add">Video Ekle (Admin)</a>
    </div>
  <% } %>
</div>

<div class="grid" style="margin-top: 14px;">
  <% if (videos == null || videos.isEmpty()) { %>
    <div class="card section col-12 muted">Henüz video yok.</div>
  <% } else { %>
    <% for (Video v : videos) { %>
      <div class="card section col-6" style="display:flex; gap:12px; align-items:center;">
        <%
          String thumb = v.getThumbPath();
          String thumbUrl = (thumb == null || thumb.trim().isEmpty())
              ? (ctx + "/assets/img/video-default.svg")
              : (thumb.startsWith("http://") || thumb.startsWith("https://"))
                  ? thumb
                  : (ctx + "/" + thumb.replaceFirst("^/+", ""));
        %>
        <img class="thumb" src="<%= thumbUrl %>" alt="Thumbnail">
        <div>
          <div style="font-weight:800;"><%= Html.esc(v.getTitle()) %></div>
          <div class="muted"><time data-epoch-ms="<%= v.getCreatedAtMs() %>"><%= Times.formatServer(v.getCreatedAtMs()) %></time></div>
          <div style="margin-top:10px;">
            <a class="btn primary" href="<%= ctx %>/video?id=<%= v.getId() %>">Video Sayfası</a>
          </div>
        </div>
      </div>
    <% } %>
  <% } %>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
