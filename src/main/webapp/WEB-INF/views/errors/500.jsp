<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<div class="card section">
  <h2>500 – Sunucu hatası</h2>
  <p class="muted">Bir sorun oluştu. Lütfen tekrar deneyin.</p>
  <a class="btn primary" href="<%= request.getContextPath() %>/home">Anasayfa</a>
</div>
<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
