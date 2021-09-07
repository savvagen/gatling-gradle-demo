#!/usr/bin/env bash
# RUN GATLING SCENARIO

GATLING_VERSION=${GATLING_VERSION:-3.6.1}

./gatling-"${GATLING_VERSION}"/bin/gatling.sh -sf src/gatling/scala/absoft/com/beats/simulations -s absoft.com.beats.simulations.LoadSimulation -rf $(pwd)

