<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layoutTop.jspf" %>
<%
  String sourceUrl = "https://www.kgm.gov.tr/SiteCollectionImages/KGMimages/Haritalar/turistik.jpg";
  String localUrl = request.getContextPath() + "/assets/img/turistik.jpg";
%>

<div class="card section">
  <h2>Harita (Pan + Zoom)</h2>
  <p class="muted">
    Sürükleyerek pan yapın, mouse tekerleği veya butonlarla zoom yapın.
    Görüntü alanı `div + overflow:hidden` ile sınırlandırılmıştır.
  </p>
  <div style="display:flex; gap:10px; flex-wrap:wrap; margin-top: 10px;">
    <button class="btn primary" id="zoomIn" type="button">Zoom +</button>
    <button class="btn" id="zoomOut" type="button">Zoom −</button>
    <button class="btn" id="reset" type="button">Sıfırla</button>
  </div>
</div>

<div class="card section" style="margin-top: 14px;">
  <div id="mapViewport"
       style="height: 520px; overflow:hidden; border-radius: 18px; border: 1px solid rgba(255,255,255,0.12); position:relative; touch-action:none;">
    <div id="mapInner" style="transform-origin: center center; will-change: transform;">
      <img src="<%= localUrl %>" alt="Türkiye Haritası" style="display:block; max-width:none;"
           onerror="this.onerror=null; this.src='<%= sourceUrl %>';">
    </div>
  </div>
  <div class="muted" style="margin-top: 8px;">
    Kaynak: <a href="<%= sourceUrl %>" target="_blank" rel="noopener"><u><%= sourceUrl %></u></a>
  </div>
</div>

<script defer src="<%= request.getContextPath() %>/assets/js/map.js"></script>
<%@ include file="/WEB-INF/views/_layoutBottom.jspf" %>
