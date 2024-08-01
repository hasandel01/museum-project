package com.bakelor.museum.service;

import com.bakelor.museum.dto.Item;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public interface MqttService extends MqttCallback {
}
