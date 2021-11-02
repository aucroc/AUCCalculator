#!/usr/bin/env bash

mvn clean
mvn package
java -jar target/auc-0.2.1-jar-with-dependencies.jar testsetlist.txt list 0.0
java -jar target/auc-0.2.1-jar-with-dependencies.jar testsetlist.txt list 0.5
java -jar target/auc-0.2.1-jar-with-dependencies.jar testsetlist.txt list 1.0
