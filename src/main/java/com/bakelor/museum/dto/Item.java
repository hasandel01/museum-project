package com.bakelor.museum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Mac")
    private String mac;

    @JsonProperty("Confidence")
    private int confidence;

    @JsonProperty("ReadAt")
    private String readAt;

    @JsonProperty("SignalStrengths")
    private List<Integer> signalStrengths;

    @JsonProperty("NegDeltaMux")
    private int negDeltaMux;

    @JsonProperty("RawRssi")
    private int rawRssi;

    @JsonProperty("RawDistance")
    private double rawDistance;

    @JsonProperty("Rssi")
    private Integer rssi;

    @JsonProperty("Distance")
    private Double distance;

    @JsonProperty("ValidDevice")
    private boolean validDevice;

    @JsonProperty("BeaconProperties")
    private List<BeaconProperty> beaconProperties;

    @JsonProperty("RawUUID")
    private String rawUUID;

    @JsonProperty("Channel")
    private int channel;

    @JsonProperty("Latitude")
    private String latitude;

    @JsonProperty("Longitude")
    private String longitude;
}
