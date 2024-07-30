package com.bakelor.museum.service;

import com.bakelor.museum.dto.BeaconData;
import com.bakelor.museum.dto.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MqttService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.clientId}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.deviceTopic}")
    private String deviceTopic;

    @Value("${mqtt.sensorTopic}")
    private String sensorTopic;

    private MqttClient client;
    private final int qos = 2;

    private BeaconData updatedData;
    private final ObjectMapper objectMapper = new ObjectMapper();  // Jackson ObjectMapper

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(broker, clientId);
            client.setCallback(this);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());

            logger.info("Connecting to broker: {}", broker);
            client.connect(connOpts);
            logger.info("Connected");

            client.subscribe(deviceTopic, qos);
            client.subscribe(sensorTopic, qos);
            logger.info("Subscribed to topics: {}, {}", deviceTopic, sensorTopic);
        } catch (MqttException e) {
            logger.error("Error connecting to MQTT broker", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                logger.info("Disconnected");
            }
        } catch (MqttException e) {
            logger.error("Error disconnecting from MQTT broker", e);
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (MqttException e) {
                logger.error("Error closing MQTT client", e);
            }
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.error("Connection lost: {}", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());

        try {
                    // Deserialize JSON payload to a BeaconData object using Jackson
                    BeaconData newData = objectMapper.readValue(payload, BeaconData.class);
                    logger.info(String.valueOf(newData));
                    if (newData != null) {
                        // Log the deserialized object
                        synchronized (this) {
                            this.updatedData = newData;
                            logger.info("Updated data: {}", updatedData);
                        }

                    } else {
                        logger.error("Deserialization returned null for payload: {}", payload);
                    }

        } catch (Exception e) {
            logger.error("Error parsing JSON", e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Delivery complete");
    }

    public BeaconData getBeaconData() {
        synchronized (this) {
            return updatedData;
        }
    }
}
