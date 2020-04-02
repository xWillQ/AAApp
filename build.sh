#!/bin/sh

kotlinc -include-runtime -cp "lib/kotlinx-cli-0.2.1.jar:lib/apache-log4j-2.13.1-bin/log4j-api-2.13.1.jar:lib/apache-log4j-2.13.1-bin/log4j-core-2.13.1.jar" -d app.jar src/
