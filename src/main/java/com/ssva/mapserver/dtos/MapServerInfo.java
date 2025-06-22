package com.ssva.mapserver.dtos;

import com.ssva.mapserver.models.Layer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapServerInfo {

    private String mapName;
    private String description;
    private List<Layer> layers;
}
