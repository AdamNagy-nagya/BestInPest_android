package com.example.nagya.bestinpest.network.RabbitMq;

import android.util.Log;

import com.example.nagya.bestinpest.network.RabbitMq.item.LobbiesRabbitMqItem;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;


/**
 * Created by nagya on 25/03/2018.
 */

public class LobbiesRabbitMq {
    static String RABBIT_URL;
    private ConnectionFactory factory;
    private Connection conn;
    private Channel channel;


    public LobbiesRabbitMq(String RABBIT_URL) {
        this.RABBIT_URL = RABBIT_URL;
        runLobbiesRabbitMq();

    }

    public void runLobbiesRabbitMq() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    factory = new ConnectionFactory();
                    factory.setUri(RABBIT_URL);
                    conn = factory.newConnection();
                    channel = conn.createChannel();
                    String queueName = channel.queueDeclare().getQueue();
                    channel.queueBind(queueName, "bip-exchange", "lobbies");
                    Consumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                                throws IOException {

                                String message = new String(body, "UTF-8");
                                Log.d("RabbitMQ ",message);
                                Gson gson = new Gson();
                                LobbiesRabbitMqItem lobbiesRabbitMqItem = gson.fromJson(message, LobbiesRabbitMqItem.class);

                                EventBus.getDefault().post( lobbiesRabbitMqItem);

                        }
                    };
                    channel.basicConsume(queueName, true, consumer);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }
}

