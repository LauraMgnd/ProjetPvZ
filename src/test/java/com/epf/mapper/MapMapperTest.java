package com.epf.mapper;

import com.epf.dto.MapDto;
import com.epf.model.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapMapperTest {

    @Test
    void testMapToDto() {
        Map map = new Map(1, 5, 9, "maps/map1.png");

        MapDto dto = MapMapper.mapToDto(map);

        assertEquals(map.getIdMap(), dto.getId_map());
        assertEquals(map.getLigne(), dto.getLigne());
        assertEquals(map.getColonne(), dto.getColonne());
        assertEquals(map.getCheminImage(), dto.getChemin_image());
    }

    @Test
    void testMapToEntity() {
        MapDto dto = new MapDto(2, 6, 10, "maps/map2.png");

        Map map = MapMapper.mapToEntity(dto);

        assertEquals(dto.getId_map(), map.getIdMap());
        assertEquals(dto.getLigne(), map.getLigne());
        assertEquals(dto.getColonne(), map.getColonne());
        assertEquals(dto.getChemin_image(), map.getCheminImage());
    }
}
