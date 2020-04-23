#!/bin/sh

if [ -z "$H2_URL" ]; then
    export H2_URL=jdbc:h2:./aaa
fi
if [ -z "$H2_LOGIN" ]; then
    export H2_LOGIN=se
fi
if [ -z "$H2_PASS" ]; then
    export H2_PASS=
fi

java -jar AAApp.jar "$@"
