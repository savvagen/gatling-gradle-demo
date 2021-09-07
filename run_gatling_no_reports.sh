#!/usr/bin/env bash
# RUN GATLING SCENARIO

GATLING_VERSION=${GATLING_VERSION:-3.6.1}

./gatling-"${GATLING_VERSION}"/bin/gatling.sh -sf src/gatling/scala/org/example/simulations -nr -s org.example.simulations.LoadSimulation > /gatling/run.log
