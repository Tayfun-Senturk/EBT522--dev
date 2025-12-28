#!/bin/sh
set -eu

# Disable the Tomcat shutdown port to avoid noisy "Invalid shutdown command" logs
# when PaaS health checks or probes hit non-HTTP ports.
sed -i 's/<Server port="8005"/<Server port="-1"/' "$CATALINA_HOME/conf/server.xml"

if [ -n "${PORT:-}" ]; then
  # Many PaaS providers inject $PORT; update Tomcat connector port.
  sed -i "s/port=\"8080\"/port=\"${PORT}\"/" "$CATALINA_HOME/conf/server.xml"
fi

exec catalina.sh run
