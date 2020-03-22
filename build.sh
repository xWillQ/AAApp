#!/bin/sh

# Create empty folder for temporary files
rm -r temp/ > /dev/null 2>&1 # Delete folder if already exists, or supress error message if doesn't
mkdir temp/

# Compile programm with kotlin runtime
kotlinc -include-runtime -cp lib/kotlinx-cli-0.2.1.jar -d temp/temp.jar src/
cp lib/kotlinx-cli-0.2.1.jar temp/

cd temp/

# Unpack programm and dependencies
jar -xf kotlinx-cli-0.2.1.jar
jar -xf temp.jar
rm kotlinx-cli-0.2.1.jar temp.jar # Remove unnecessary files to make final jar lighter

# Merge programm with dependencies
jar -cfe app.jar com.kafedra.bd.MainKt .

cd ..
mv temp/app.jar app.jar

rm -r temp/
