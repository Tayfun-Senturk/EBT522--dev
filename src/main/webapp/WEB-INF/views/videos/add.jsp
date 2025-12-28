<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  String ctx = request.getContextPath();
%>

<div class="card section">
  <h2>Video Ekle (Admin)</h2>
  <p class="muted">YouTube video ID giriniz (örn: `dQw4w9WgXcQ`).</p>

  <form method="post" action="<%= ctx %>/videos/add">
    <label>Başlık</label>
    <input class="input" name="title" placeholder="Örn: Sınıf Tanıtımı">

    <label>YouTube Video ID *</label>
    <input class="input" name="youtube_id" placeholder="dQw4w9WgXcQ" required>

    <div style="display:flex; gap:10px; margin-top: 14px;">
      <button class="btn primary" type="submit">Ekle</button>
      <a class="btn" href="<%= ctx %>/videos">Vazgeç</a>
    </div>
  </form>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
