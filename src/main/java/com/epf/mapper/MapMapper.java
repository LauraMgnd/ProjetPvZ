package com.epf.mapper;

import com.epf.dto.MapDto;
import com.epf.model.Map;

public class MapMapper {

    public static MapDto mapToDto(Map map) {
        return new MapDto(
                map.getIdMap(),
                map.getLigne(),
                map.getColonne(),
                map.getCheminImage()
        );
    }

    public static Map mapToEntity(MapDto dto) {
        return new Map(
                dto.getId_map(),
                dto.getLigne(),
                dto.getColonne(),
                dto.getChemin_image()
        );
    }
}
