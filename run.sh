#!/bin/sh

export LOG4J_CONFIGURATION_FILE="$PWD/log4j2-test.xml"
if [ -z "$H2_URL" ]; then
    export H2_URL=jdbc:h2:./aaa
fi
if [ -z "$H2_LOGIN" ]; then
    export H2_LOGIN=se
fi
if [ -z "$H2_PASS" ]; then
    export H2_PASS=
fi

java --class-path "lib/kotlinx-cli-0.2.1.jar:lib/apache-log4j-2.13.1-bin/log4j-api-2.13.1.jar:lib/apache-log4j-2.13.1-bin/log4j-core-2.13.1.jar:app.jar:lib/h2-1.4.200.jar" com.kafedra.bd.MainKt "$@"