(function () {
  function clamp(v, min, max) {
    return Math.max(min, Math.min(max, v));
  }

  function init() {
    var viewport = document.getElementById("mapViewport");
    var inner = document.getElementById("mapInner");
    if (!viewport || !inner) return;

    var state = { x: 0, y: 0, scale: 1, dragging: false, lastX: 0, lastY: 0 };

    function apply() {
      inner.style.transform = "translate(" + state.x + "px," + state.y + "px) scale(" + state.scale + ")";
    }

    function setScale(nextScale, centerX, centerY) {
      var prev = state.scale;
      state.scale = clamp(nextScale, 0.6, 4);

      var rect = viewport.getBoundingClientRect();
      var cx = centerX == null ? rect.left + rect.width / 2 : centerX;
      var cy = centerY == null ? rect.top + rect.height / 2 : centerY;

      var dx = cx - rect.left - rect.width / 2;
      var dy = cy - rect.top - rect.height / 2;

      var factor = state.scale / prev;
      state.x = dx - (dx - state.x) * factor;
      state.y = dy - (dy - state.y) * factor;
      apply();
    }

    function reset() {
      state.x = 0;
      state.y = 0;
      state.scale = 1;
      apply();
    }

    viewport.addEventListener("pointerdown", function (e) {
      state.dragging = true;
      state.lastX = e.clientX;
      state.lastY = e.clientY;
      viewport.setPointerCapture(e.pointerId);
    });

    viewport.addEventListener("pointermove", function (e) {
      if (!state.dragging) return;
      var dx = e.clientX - state.lastX;
      var dy = e.clientY - state.lastY;
      state.lastX = e.clientX;
      state.lastY = e.clientY;
      state.x += dx;
      state.y += dy;
      apply();
    });

    viewport.addEventListener("pointerup", function () {
      state.dragging = false;
    });
    viewport.addEventListener("pointercancel", function () {
      state.dragging = false;
    });

    viewport.addEventListener(
      "wheel",
      function (e) {
        e.preventDefault();
        var delta = e.deltaY > 0 ? -0.1 : 0.1;
        setScale(state.scale + delta, e.clientX, e.clientY);
      },
      { passive: false }
    );

    document.getElementById("zoomIn").addEventListener("click", function () {
      setScale(state.scale + 0.2);
    });
    document.getElementById("zoomOut").addEventListener("click", function () {
      setScale(state.scale - 0.2);
    });
    document.getElementById("reset").addEventListener("click", reset);

    apply();
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();

