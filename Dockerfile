FROM gradle:6.5-jdk8

# Upgrade system and repo. list
RUN apt-get update -y && \
     apt-get upgrade -y && \
     apt-get dist-upgrade -y && \
     apt-get -y autoremove && \
     apt-get clean

# Install nessesary dependencyes
RUN apt-get update && \
    apt-get install -y software-properties-common wget curl zip unzip
RUN apt install openjdk-8-jdk python3 python3-doc clang golang-go gcc g++ -y

# Install Sdkman and Scala
RUN curl -s "https://get.sdkman.io" | bash
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh; sdk version && sdk list scala && sdk install scala 2.13.6"

#RUN mkdir -p /performance
#WORKDIR /performance

ENV GATLING_VERSION=3.6.1

# Download Gatling
#RUN apt-get update \
#    #&& curl https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/${GATLING_VERSION}/gatling-charts-highcharts-bundle-${GATLING_VERSION}-bundle.zip -L -o gatling-${GATLING_VAERSION}.zip  \
#    && wget https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/${GATLING_VERSION}/gatling-charts-highcharts-bundle-${GATLING_VERSION}-bundle.zip  \
#    && unzip gatling-charts-highcharts-bundle-${GATLING_VERSION}-bundle.zip -x \
#    && mv gatling-charts-highcharts-bundle-${GATLING_VERSION} gatling-${GATLING_VERSION} \
#    && rm -r gatling-charts-highcharts-bundle-${GATLING_VERSION}-bundle.zip \
#    && ls -la

