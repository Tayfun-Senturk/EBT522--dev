(function () {
  function renderLocalTimes() {
    var nodes = document.querySelectorAll("[data-epoch-ms]");
    for (var i = 0; i < nodes.length; i++) {
      var el = nodes[i];
      var raw = el.getAttribute("data-epoch-ms");
      if (!raw) continue;
      var ms = Number(raw);
      if (!Number.isFinite(ms)) continue;
      var d = new Date(ms);
      el.textContent = d.toLocaleString();
      el.setAttribute("title", "İstemci saati");
    }
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", renderLocalTimes);
  } else {
    renderLocalTimes();
  }
})();

