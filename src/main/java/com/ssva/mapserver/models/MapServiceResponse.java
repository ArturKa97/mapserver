package com.ssva.mapserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapServiceResponse {
    private String mapName;
    private String description;
    private List<Layer> layers;
}
