package com.bakelor.museum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hub {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("HubId")
    private String hubId;

    @JsonProperty("Distance")
    private double distance;

    @JsonProperty("Rssi")
    private double rssi;

    @JsonProperty("ProximityOrder")
    private double proximityOrder;

    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("ReadAt")
    private String readAt;
}
