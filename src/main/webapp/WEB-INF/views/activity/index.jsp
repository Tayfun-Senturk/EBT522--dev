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
  <h2>Kim, Nerede, Ne Yapıyor?</h2>
  <p class="muted">Üyelerin veritabanındaki bilgileri tablo halinde listelenir.</p>
  <% if (auth != null && auth.isAdmin()) { %>
    <div class="flash">Admin: Kullanıcı silme / admin atama işlemleri bu sayfadan yapılabilir.</div>
  <% } %>
</div>

<div class="card section" style="margin-top: 14px;">
  <table class="table">
    <thead>
      <tr>
        <th>Ad</th>
        <th>Soyad</th>
        <th>Adres</th>
        <th>Telefon</th>
        <th>Şimdiki Okulu</th>
        <th>İşi</th>
        <th>E‑posta</th>
        <th>Web</th>
        <th>Facebook</th>
        <th>Twitter</th>
        <th>Rol</th>
        <th>İşlem</th>
      </tr>
    </thead>
    <tbody>
      <% if (people == null || people.isEmpty()) { %>
        <tr><td colspan="12" class="muted">Kayıtlı üye yok.</td></tr>
      <% } else { %>
        <% for (UserProfile p : people) { %>
          <tr>
            <td><%= Html.esc(p.getFirstName()) %></td>
            <td><%= Html.esc(p.getLastName()) %></td>
            <td><%= Html.esc(p.getAddress()) %></td>
            <td><%= Html.esc(p.getPhone()) %></td>
            <td><%= Html.esc(p.getCurrentSchool()) %></td>
            <td><%= Html.esc(p.getCurrentJob()) %></td>
            <td><%= Html.esc(p.getEmail()) %></td>
            <td><%= Html.esc(p.getWebUrl()) %></td>
            <td><%= Html.esc(p.getFacebookId()) %></td>
            <td><%= Html.esc(p.getTwitterHandle()) %></td>
            <td><%= Html.esc(p.getRole()) %></td>
            <td>
              <a class="btn" href="<%= ctx %>/person?id=<%= p.getId() %>">Kişi</a>
              <% if (auth != null && auth.isAdmin()) { %>
                <form method="post" action="<%= ctx %>/activity" style="display:inline;">
                  <input type="hidden" name="action" value="<%= p.isAdmin() ? "make_user" : "make_admin" %>">
                  <input type="hidden" name="user_id" value="<%= p.getId() %>">
                  <button class="btn" type="submit"><%= p.isAdmin() ? "User Yap" : "Admin Yap" %></button>
                </form>
                <form method="post" action="<%= ctx %>/activity" style="display:inline;">
                  <input type="hidden" name="action" value="delete_user">
                  <input type="hidden" name="user_id" value="<%= p.getId() %>">
                  <button class="btn danger" type="submit" onclick="return confirm('Kullanıcı silinsin mi?')">Sil</button>
                </form>
              <% } %>
            </td>
          </tr>
        <% } %>
      <% } %>
    </tbody>
  </table>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
