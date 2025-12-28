<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ebt522.social.model.UserProfile" %>
<%@ page import="tr.edu.ebt522.social.util.Html" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  List<UserProfile> people = (List<UserProfile>) request.getAttribute("people");
  String ctx = request.getContextPath();
%>

<div class="card section">
  <h2>Kişiler</h2>
  <p class="muted">Tüm öğrencilerin küçük resimleri ve kişi sayfası bağlantıları.</p>
</div>

<div class="grid" style="margin-top: 14px;">
  <% if (people == null || people.isEmpty()) { %>
    <div class="card section col-12 muted">Henüz kayıtlı kişi yok.</div>
  <% } else { %>
    <% for (UserProfile p : people) { %>
      <div class="card section col-6" style="display:flex; gap:12px; align-items:center;">
        <%
          String avatar = p.getAvatarPath();
          String avatarUrl = (avatar == null || avatar.trim().isEmpty())
              ? (ctx + "/assets/img/avatar-default.svg")
              : (avatar.startsWith("http://") || avatar.startsWith("https://"))
                  ? avatar
                  : (ctx + "/" + avatar.replaceFirst("^/+", ""));
        %>
        <img class="thumb" src="<%= avatarUrl %>" alt="Profil">
        <div>
          <div style="font-weight:800;"><%= Html.esc(p.getFullName()) %></div>
          <div class="muted">@<%= Html.esc(p.getUsername()) %></div>
          <div style="margin-top:10px;">
            <a class="btn primary" href="<%= ctx %>/person?id=<%= p.getId() %>">Kişi Sayfası</a>
          </div>
        </div>
      </div>
    <% } %>
  <% } %>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
