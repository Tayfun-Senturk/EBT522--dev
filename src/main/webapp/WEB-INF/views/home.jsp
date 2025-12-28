<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<div class="card hero">
  <h1>Sınıf Yıllığı / Mezunlar Platformu</h1>
  <p>
    Kayıt olun, oturum açın; forumda mesajlaşın; kişiler, fotoğraflar ve videolar üzerinden yorum bırakın.
    Tarih‑saatler istemci cihazınızın saatine göre gösterilir.
  </p>
</div>

<div class="grid">
  <div class="card section col-6">
    <h2>Hızlı Başlangıç</h2>
    <p class="muted">Ödevde istenen sayfalara buradan da ulaşabilirsiniz.</p>
    <div style="display:flex; flex-wrap:wrap; gap:10px; margin-top:12px;">
      <a class="btn primary" href="<%= request.getContextPath() %>/register">Kişi Kayıt</a>
      <a class="btn" href="<%= request.getContextPath() %>/login">Oturum Açma</a>
      <a class="btn" href="<%= request.getContextPath() %>/forum">Forum</a>
      <a class="btn" href="<%= request.getContextPath() %>/people">Kişiler</a>
      <a class="btn" href="<%= request.getContextPath() %>/photos">Fotoğraflar</a>
      <a class="btn" href="<%= request.getContextPath() %>/videos">Videolar</a>
      <a class="btn" href="<%= request.getContextPath() %>/activity">Kim, Nerede, Ne Yapıyor?</a>
      <a class="btn" href="<%= request.getContextPath() %>/map">Harita</a>
    </div>
  </div>

  <div class="card section col-6">
    <h2>Notlar</h2>
    <ul class="muted" style="margin:0; padding-left:18px; line-height:1.6;">
      <li>Forum ve yorum yazma işlemleri için giriş gereklidir.</li>
      <li>Admin kullanıcılar mesajları düzenleyip silebilir ve kullanıcı yönetimi yapabilir.</li>
      <li>Harita sayfasında pan/zoom uygulanmıştır.</li>
    </ul>
  </div>
</div>

<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
