# Gatling Docker Image

This container allows [Gatling](http://gatling.io/#/) to be ran within a [Docker](https://www.docker.com) container. It can be ran against a website or other online service. Please see https://hub.docker.com/r/davey/gatling-container/ for further information.

## Running on CI

This container can be used locally or on CI server such as Jenkins. The workflow could enable load testing as a part of CI rather than at the end of a deliverable. However, on a shared build environment this may not make you very popular.

## Output

All results will be placed into the results directory.

## Building for Local Use

```sh
docker build -t davey/gatling .
```

## Run Locally

To run locally,

```sh
$ docker run --rm=true -v /host/path/to/gatling-docker/gatling/user-files/:/opt/gatling/user-files -v /host/path/to/gatling-docker/gatling/results:/opt/gatling/results davey/gatling -m -s TestSimulation -rf /opt/gatling/results
```

OR

```sh
$ docker run --rm=true --name gatling davey/gatling -s TestSimulation
```
