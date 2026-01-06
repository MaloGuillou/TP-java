#!/bin/bash

mkdir -p out

find src -name "*.java" > sources.txt
javac -d out @sources.txt

java -cp out:resources com.malog.esiea.monsters.Main

rm sources.txt
