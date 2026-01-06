#!/bin/bash

rm -rf out
mkdir out

find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt

cp -r src/main/resources/* out/

if [ $? -eq 0 ]; then
    java -cp out com.malog.esiea.monsters.Main
fi

rm sources.txt
