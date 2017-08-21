FROM java:openjdk-8-jre

RUN mkdir -p /opt/results

WORKDIR /opt

#RUN apt-get update -y && apt-get install wget -y
#RUN wget -O gatling.zip https://oss.sonatype.org/content/repositories/snapshots/io/gatling/highcharts/gatling-charts-highcharts-bundle/2.2.0-SNAPSHOT/gatling-charts-highcharts-bundle-2.2.0-20150928.195526-367-bundle.zip
#RUN unzip gatling.zip

COPY src/gatling-charts-highcharts-bundle-2.2.0-SNAPSHOT.zip gatling.zip

RUN unzip gatling.zip
RUN mv gatling-charts-highcharts-bundle-2.2.0-SNAPSHOT/ gatling

COPY bin/gatling.sh /opt/gatling/bin/gatling.sh
COPY bin/gatling.conf /opt/gatling/conf/gatling.conf
COPY user-files/simulations/ /opt/gatling/user-files/simulations

RUN cat /opt/gatling/conf/gatling.conf

WORKDIR /opt/gatling

RUN chmod 755 /opt/gatling/bin/gatling.sh

ENV PATH /opt/gatling/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
ENV GATLING_HOME /opt/gatling

RUN echo "Simulations"
RUN ls -la user-files/simulations
RUN rm -rf user-files/simulations/computerdatabase

ENTRYPOINT ["gatling.sh"]
CMD ["-m", -rf /opt/gatling/results"]