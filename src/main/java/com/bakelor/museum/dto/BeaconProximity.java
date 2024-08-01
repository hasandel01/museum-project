package com.bakelor.museum.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BeaconProximity {

    @JsonProperty("Timeout")
    private int timeout;

    @JsonProperty("BeaconId")
    private String beaconId;

    @JsonProperty("Hubs")
    private List<Hub> hubs;

    @JsonProperty("CreatedAt")
    private String createdAt;

}
