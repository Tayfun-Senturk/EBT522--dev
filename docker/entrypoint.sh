#!/bin/sh
set -eu

if [ -n "${PORT:-}" ]; then
  # Many PaaS providers inject $PORT; update Tomcat connector port.
  sed -i "s/port=\"8080\"/port=\"${PORT}\"/" "$CATALINA_HOME/conf/server.xml"
fi

exec catalina.sh run

