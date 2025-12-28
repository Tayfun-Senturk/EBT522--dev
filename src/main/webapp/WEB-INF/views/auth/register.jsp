<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  String error = (String) request.getAttribute("error");
  if (error != null) {
%>
  <div class="flash error"><%= error %></div>
<% } %>

<div class="card section">
  <h2>Kişi Kayıt</h2>
  <p class="muted">Zorunlu alanlar: ad, soyad, kullanıcı adı, e‑posta, şifre.</p>

  <form method="post" action="<%= request.getContextPath() %>/register">
    <div class="grid" style="margin-top: 8px;">
      <div class="col-6">
        <label>Ad *</label>
        <input class="input" name="first_name" value="<%= request.getParameter("first_name") != null ? request.getParameter("first_name") : "" %>" required>
      </div>
      <div class="col-6">
        <label>Soyad *</label>
        <input class="input" name="last_name" value="<%= request.getParameter("last_name") != null ? request.getParameter("last_name") : "" %>" required>
      </div>

      <div class="col-6">
        <label>Kullanıcı Adı *</label>
        <input class="input" name="username" value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" required>
      </div>
      <div class="col-6">
        <label>E‑posta *</label>
        <input class="input" type="email" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
      </div>

      <div class="col-6">
        <label>Şifre *</label>
        <input class="input" type="password" name="password" required>
      </div>
      <div class="col-6">
        <label>Şifre (tekrar) *</label>
        <input class="input" type="password" name="password2" required>
      </div>

      <div class="col-6">
        <label>Cinsiyet (radio)</label>
        <div style="display:flex; gap:12px; flex-wrap:wrap;">
          <label style="margin:0;"><input type="radio" name="gender" value="M" <%= "M".equals(request.getParameter("gender")) ? "checked" : "" %>> Erkek</label>
          <label style="margin:0;"><input type="radio" name="gender" value="F" <%= "F".equals(request.getParameter("gender")) ? "checked" : "" %>> Kadın</label>
          <label style="margin:0;"><input type="radio" name="gender" value="OTHER" <%= "OTHER".equals(request.getParameter("gender")) ? "checked" : "" %>> Diğer</label>
        </div>
      </div>

      <div class="col-6">
        <label>Oturduğu Şehir (combobox/select)</label>
        <select name="city">
          <option value="">Seçiniz</option>
          <%
            String[] cities = {"Adana","Ankara","Antalya","Bursa","Diyarbakır","Erzurum","Eskişehir","Gaziantep","İstanbul","İzmir","Kayseri","Konya","Mersin","Samsun","Trabzon","Van"};
            String city = request.getParameter("city");
            for (String c : cities) {
          %>
            <option value="<%= c %>" <%= c.equals(city) ? "selected" : "" %>><%= c %></option>
          <% } %>
        </select>
      </div>

      <div class="col-12">
        <label>Hobiler (checkbox)</label>
        <%
          String[] selected = request.getParameterValues("hobbies");
          java.util.Set<String> sel = new java.util.HashSet<>();
          if (selected != null) for (String s : selected) sel.add(s);
          String[] hobbies = {"müzik","sinema","spor","kitap","yazılım","fotoğraf","seyahat","oyun"};
          for (String h : hobbies) {
        %>
          <label style="display:inline-flex; align-items:center; gap:8px; margin:6px 12px 0 0;">
            <input type="checkbox" name="hobbies" value="<%= h %>" <%= sel.contains(h) ? "checked" : "" %>>
            <span><%= h %></span>
          </label>
        <% } %>
      </div>

      <div class="col-6">
        <label>Doğum Tarihi</label>
        <input class="input" type="date" name="birth_date" value="<%= request.getParameter("birth_date") != null ? request.getParameter("birth_date") : "" %>">
      </div>
      <div class="col-6">
        <label>Telefon</label>
        <input class="input" type="tel" name="phone" value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>">
      </div>

      <div class="col-12">
        <label>Adres (textarea)</label>
        <textarea name="address"><%= request.getParameter("address") != null ? request.getParameter("address") : "" %></textarea>
      </div>

      <div class="col-12">
        <label>Hakkımda (textarea)</label>
        <textarea name="about"><%= request.getParameter("about") != null ? request.getParameter("about") : "" %></textarea>
      </div>

      <div class="col-6">
        <label>Şimdiki Okulu</label>
        <input class="input" name="current_school" value="<%= request.getParameter("current_school") != null ? request.getParameter("current_school") : "" %>">
      </div>
      <div class="col-6">
        <label>İşi</label>
        <input class="input" name="current_job" value="<%= request.getParameter("current_job") != null ? request.getParameter("current_job") : "" %>">
      </div>

      <div class="col-6">
        <label>Web Adresi (url)</label>
        <input class="input" type="url" name="web_url" value="<%= request.getParameter("web_url") != null ? request.getParameter("web_url") : "" %>">
      </div>
      <div class="col-6">
        <label>Facebook ID</label>
        <input class="input" name="facebook_id" value="<%= request.getParameter("facebook_id") != null ? request.getParameter("facebook_id") : "" %>">
      </div>
      <div class="col-6">
        <label>Twitter</label>
        <input class="input" name="twitter_handle" value="<%= request.getParameter("twitter_handle") != null ? request.getParameter("twitter_handle") : "" %>">
      </div>
    </div>

    <div style="display:flex; gap:10px; margin-top: 14px;">
      <button class="btn primary" type="submit">Kayıt Ol</button>
      <a class="btn" href="<%= request.getContextPath() %>/login">Zaten üyeyim</a>
    </div>
  </form>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
