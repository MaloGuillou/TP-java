# How to Run
This project requires Java 17.
Preferably, use a Linux distribution.
It may not run on Windows, if it does, it will probably be ugly.

## Automated script
1. Open a terminal in the project root folder (`TP-java`).
2. Run the following commands:
```bash
chmod +x run.sh
./run.sh
```

## Manual steps
### 1. Open a terminal in the project root folder (`TP-java`).
### 2. Create the output directory
```bash
mkdir -p out
```
### 3. Compile the source code
```bash
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
rm sources.txt
```
### 4. Copy resource files (Required)
```bash
cp -r src/main/resources/* out/
```
### 5. Run the application
```bash
java -cp out com.malog.esiea.monsters.Main
```
## To clean previous builds
```bash
rm -rf out
```