#!/bin/sh

kotlinc -include-runtime -cp lib/kotlinx-cli-0.2.1.jar -d app.jar src/
