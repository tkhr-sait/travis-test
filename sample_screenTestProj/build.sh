#!/bin/sh

if [ ! -f target/scenario-test-0.0.1-SNAPSHOT-jar-with-dependencies.jar ]; then
  mvn package
fi

