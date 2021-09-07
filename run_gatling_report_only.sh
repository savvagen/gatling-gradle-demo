#!/usr/bin/env bash
# GENERATE REPORT ONLY

GATLING_VERSION=${GATLING_VERSION:-3.6.1}

./gatling-"${GATLING_VERSION}"/bin/gatling.sh -ro $(pwd)/results/*
