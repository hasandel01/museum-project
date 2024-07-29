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
public class BeaconProperty {

    @JsonProperty("Type")
    private int type;

    @JsonProperty("Values")
    private List<Integer> values;

    @JsonProperty("Value")
    private int value;
}
