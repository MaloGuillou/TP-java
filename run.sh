!/bin/bash

rm -rf out
mkdir -p out

echo "Compiling..."
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
rm sources.txt

if [ -d "src/main/resources" ] && [ "$(ls -A src/main/resources)" ]; then
    cp -r src/main/resources/* out/
fi

echo "Starting Game..."
java -cp out com.malog.esiea.monsters.Main