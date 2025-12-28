<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  String error = (String) request.getAttribute("error");
  if (error != null) {
%>
  <div class="flash error"><%= error %></div>
<% } %>

<div class="card section">
  <h2>Oturum Açma</h2>
  <p class="muted">Kullanıcı adınızı veya e‑postanızı kullanabilirsiniz.</p>

  <form method="post" action="<%= request.getContextPath() %>/login">
    <label>Kullanıcı adı / E‑posta</label>
    <input class="input" name="login" value="<%= request.getParameter("login") != null ? request.getParameter("login") : "" %>" required>

    <label>Şifre</label>
    <input class="input" type="password" name="password" required>

    <div style="display:flex; gap:10px; margin-top: 14px;">
      <button class="btn primary" type="submit">Giriş Yap</button>
      <a class="btn" href="<%= request.getContextPath() %>/register">Kayıt Ol</a>
    </div>
  </form>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
