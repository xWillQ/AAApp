#!/bin/sh

kotlinc -include-runtime -d app.jar src/ -cp "\
lib/kotlinx-cli-0.2.1.jar:\
lib/apache-log4j-2.13.1-bin/log4j-api-2.13.1.jar:\
lib/apache-log4j-2.13.1-bin/log4j-core-2.13.1.jar:\
lib/h2-1.4.200.jar:\
lib/flyway-core-6.3.2.jar"
