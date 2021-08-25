# Phenopacket Generator: phepagen

The purpose of this app is to generate and output example phenopackets (version 2) that we can use elsewhere to test validation and analysis code. To run the code, 
do the following.

```
git clone https://github.com/pnrobinson/phepagen.git
cd phepagen
mvn package
java -jar target/phepagen.jar
```

The program will write phenopacket files into a new directory called ``data``.
