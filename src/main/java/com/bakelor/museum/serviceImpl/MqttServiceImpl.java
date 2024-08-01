package com.bakelor.museum.serviceImpl;

import com.bakelor.museum.dto.BeaconData;
import com.bakelor.museum.dto.BeaconProximity;
import com.bakelor.museum.handler.WebSocketHandler;
import com.bakelor.museum.service.MqttService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Service
public class MqttServiceImpl implements MqttService {

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

    @Value("${mqtt.nearestTopic}")
    private String nearestTopic;

    private MqttClient client;

    private final WebSocketHandler webSocketHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public MqttServiceImpl(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

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

            int qos = 2;
            client.subscribe(deviceTopic, qos);
            client.subscribe(sensorTopic, qos);
            client.subscribe(nearestTopic, qos);
            logger.info("Subscribed to topics: {}, {}, {}", deviceTopic, sensorTopic, nearestTopic);
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
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());

        try {
            if(topic.equals(sensorTopic)) {
                BeaconData beaconData = objectMapper.readValue(payload, BeaconData.class);
                webSocketHandler.sendMessage(beaconData);
            }
            else if(topic.equals(nearestTopic + "/" + )) {
                BeaconProximity beaconProximity = objectMapper.readValue(payload, BeaconProximity.class);
                webSocketHandler.sendMessage(beaconProximity);
            }
            else
                    logger.info("Data in hand is null!");

        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Delivery complete");
    }

}
