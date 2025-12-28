<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  String ctx = request.getContextPath();
%>

<div class="card section">
  <h2>Fotoğraf Ekle (Admin)</h2>
  <p class="muted">Resim yolları; `assets/...` gibi context‑relative veya tam URL olabilir.</p>

  <form method="post" action="<%= ctx %>/photos/add">
    <label>Başlık</label>
    <input class="input" name="title" placeholder="Örn: Gezi Fotoğrafı">

    <label>Büyük Fotoğraf Yolu *</label>
    <input class="input" name="image_path" placeholder="assets/img/photos/photo-3.png" required>

    <label>Thumbnail Yolu *</label>
    <input class="input" name="thumb_path" placeholder="assets/img/photos/photo-3-thumb.png" required>

    <div style="display:flex; gap:10px; margin-top: 14px;">
      <button class="btn primary" type="submit">Ekle</button>
      <a class="btn" href="<%= ctx %>/photos">Vazgeç</a>
    </div>
  </form>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
