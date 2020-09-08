package com.knowin.iot.command;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.json.JsonObject;

public class Command {

    private static final int DEFAULT_RECV_BUFFER_SIZE = 1024;
    private static final int DEFAULT_SEND_BUFFER_SIZE = 1024;
    private final DatagramSocket socket;

    public Command(Vertx vertx) {
        DatagramSocketOptions options = new DatagramSocketOptions();
        options.setBroadcast(true);
        options.setIpV6(false);
        options.setLoopbackModeDisabled(true);
        options.setReuseAddress(true);
        options.setReusePort(true);
        options.setReceiveBufferSize(DEFAULT_RECV_BUFFER_SIZE);
        options.setSendBufferSize(DEFAULT_SEND_BUFFER_SIZE);
        socket = vertx.createDatagramSocket(options);
    }

    private Future<DatagramSocket> send(Buffer buffer, int port, String host) {
        Promise<DatagramSocket> promise = Promise.promise();
        socket.send(buffer, port, host, promise);
        return promise.future();
    }

    /**
     * 10.0.1.255:8888
     *
     * {
     *     "channel": 1,
     *     "value": true
     * }
     *
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("usage: java -jar xxxx.jar <address> <port> <channel> <value>");
            System.out.println("example: java -jar xxxx.jar 10.0.1.255 8888 1 1");
            System.out.println("example: java -jar xxxx.jar 192.168.1.255 8888 16 0");
            return;
        }

        String address = args[0];
        int port = Integer.parseInt(args[1]);
        int channel = Integer.parseInt(args[2]);
        boolean onoff = (Integer.parseInt(args[3]) == 1);

        JsonObject o = new JsonObject();
        o.put("channel", channel);
        o.put("value", onoff);

        Command command = new Command(Vertx.vertx());
        command.send(o.toBuffer(), port, address).onComplete(ar -> {
            if (ar.succeeded()) {
                System.out.println("send ok: " + o.encode());
            } else {
                System.out.println("send failed: " + ar.cause().getMessage() + ", message: " + o.encode());
            }
        });
    }
}
