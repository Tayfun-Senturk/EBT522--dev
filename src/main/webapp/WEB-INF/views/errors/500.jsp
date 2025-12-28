<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<div class="card section">
  <h2>500 - Sunucu hatası</h2>
  <p class="muted">Bir sorun oluştu. Lütfen tekrar deneyin.</p>
  <%
    String debug = System.getenv("APP_DEBUG");
    boolean show = debug != null && (debug.equalsIgnoreCase("true") || debug.equals("1"));
    Throwable ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
    if (show && ex != null) {
  %>
    <div class="card section" style="margin-top:12px;">
      <div class="muted" style="margin-bottom:6px;">Hata detayı (APP_DEBUG açık):</div>
      <pre style="white-space:pre-wrap;"><%= ex.getClass().getName() %>: <%= ex.getMessage() %></pre>
    </div>
  <% } %>
  <a class="btn primary" href="<%= request.getContextPath() %>/home">Anasayfa</a>
</div>
<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
