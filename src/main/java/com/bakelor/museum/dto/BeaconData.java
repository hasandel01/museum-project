package com.bakelor.museum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeaconData {
    @JsonProperty("HubId")
    private String hubId;

    @JsonProperty("SampleCount")
    private int sampleCount;

    @JsonProperty("BakelorValidSampleCount")
    private int bakelorValidSampleCount;

    @JsonProperty("BakelorSampleCount")
    private int bakelorSampleCount;

    @JsonProperty("HeapSize")
    private int heapSize;

    @JsonProperty("Items")
    private List<Item> items;

    @JsonProperty("RemoveBeaconLimit")
    private boolean removeBeaconLimit;

    @JsonProperty("StaSignal")
    private int staSignal;

    @JsonProperty("Voltage")
    private int voltage;

    @JsonProperty("ChunkCreatedAt")
    private String chunkCreatedAt;

    @JsonProperty("UniqueSampleCount")
    private int uniqueSampleCount;

    @JsonProperty("SessionId")
    private long sessionId;

    @JsonProperty("ContentType")
    private int contentType;
}
