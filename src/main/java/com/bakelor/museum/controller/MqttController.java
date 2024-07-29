package com.bakelor.museum.controller;


import com.bakelor.museum.dto.BeaconData;
import com.bakelor.museum.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class MqttController {

    private final MqttService mqttService;

    @Autowired
    public MqttController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @GetMapping("/beaconProperties")
    public BeaconData getBeaconProperties() {
        return mqttService.getBeaconData();
    }
}


