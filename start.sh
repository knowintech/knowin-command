#!/bin/sh


java -Dlogback.configurationFile=config/logback-client.xml \
     -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory \
     -jar xcp-vertx-ws-device-client-example/build/libs/xcp-vertx-ws-device-client-example-0.88.0-SNAPSHOT-fat.jar
