# Gatling + InfluxDB + Grafana Docker Stack

A simple [Docker](https://www.docker.com) stack for using [Gatling](http://gatling.io) with [InfluxDB](https://www.influxdata.com) and [Grafana](https://grafana.com) to get a real time results from load testing.

Please note, it may be easier to run Gatling outside of Docker when testing your work locally, as log files and results become easier to inspect. The performance of the running service could also become a factor depending on the OS/environment you are running Docker on.

## Gatling

If you want to run http://gatling.io inside a https://www.docker.com container, there are numberous images available via the Docker registry. It is also worth inspecting the license for the bundled version used, for example, the use of [Gatling Highcharts](https://github.com/gatling/gatling-highcharts/blob/master/LICENSE) to create simulation reports had some constraints at the time of writing.

## Docker Images

The two images for this setup are Grafana and  InfluxDB for the backend database, which is where the results to the simulations can be stored. For reference, the InfluxDB image was based off of the [Tutum image](https://hub.docker.com/r/tutum/influxdb/). The images used for this stack are pulled from the main Docker registry, or you can create your own using a custom Dockerfile. Within the main [InfluxDB](./infludb) and [Grafana](./grafana) directories, you can use `ONBUILD` to add references a custom config.toml file and config.js respectively.

## Setup

A [docker-machine](https://docs.docker.com/machine/) will need to be created first, for example, to create a machine called `sandbox`, which will contain all the associated [images](https://docs.docker.com/engine/reference/commandline/images/), run the following commands:

```bash
$ docker-machine create -d virtualbox sandbox
$ docker-machine env sandbox
$ eval $(docker-machine env sandbox)
$ docker-machine ip sandbox # you'll need this later, so please make a note
```

In order to view the Grafana dashboard you may also need to set-up port forwarding to the [sandbox manually](https://github.com/boot2docker/boot2docker/blob/master/doc/WORKAROUNDS.md#port-forwarding). To do this you'll need to stop the machine, modify the machines ports, and then start the machine again.

```bash
$ docker-machine status sandbox   # check the machine's status
$ docker-machine stop sandbox     # stop the machine if it is already running
$ VBoxManage modifyvm "sandbox" --natpf1 "tcp-port8081,tcp,,8081,,8081"
$ VBoxManage modifyvm "sandbox" --natpf1 "tcp-port8086,tcp,,8086,,8086"
$ docker-machine start sandbox    # start the machine if it is already running
```

## Start the Stack

The simplest way to start the stack is to call the [./start](./start) script. Once the images have been built you should see the container ID's listed via `docker ps -a`. The following services should now be accessable via your browser:

- Grafana interface, e.g., [http://192.168.99.100:8081/](http://192.168.99.100:8081/)
- InfluxDB interface e.g., [http://192.168.99.100:8083/](http://192.168.99.100:8083/)

The first request should return the newly created Grafana dashboard. And as long as the InfluxDB image is also running, you should also be able to save edits to the dashboard. However, please be aware that if InfluxDB goes down, all data will be lost, so it's worth taking backups of the dashboard configuration regularly. All the build options associated to each service can be found in their respective build directories.

To view InfluxDB content directly, login via the web interface and click the Gatling database tab to view this data. Once you've accessed the `Data Interface`, you can type `list series` to view all data in the dashboard, etc. InfluxDB uses a SQL-ish query language, so queries should be straight forward once you are familiar the data structure.

### Writing Load Test Data (Gatling)

Please view the [Gatling README](./gatling/README.md) to get [Gatling](http://gatling.io) up and running.

A sample [gatling.conf](./gatling/src/gatling.conf) has been included which is mostly the vanilla configuration and  [Graphite](https://graphiteapp.org) options as InfluxDB accepts data using the Graphite protocol, so by using these options, data will still go into the database as if it were really a [Whisper database](https://graphite.readthedocs.io/en/latest/whisper.html) commonly used by [Graphite](https://graphiteapp.org).

Please ensure that the `host` [configuration](./gatling/gatling.conf) needs to reflect the output from `docker-machine ip sandbox` else no output data will be registered.
