package com.epf.controller;

import com.epf.dto.MapDto;
import com.epf.model.Map;
import com.epf.service.MapService;
import com.epf.service.ZombieService;
import com.epf.mapper.MapMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapControllerTest {

    @InjectMocks
    private MapController mapController;

    @Mock
    private MapService mapService;

    @Mock
    private ZombieService zombieService;

    private MapDto mapDto;
    private Map map;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapDto = new MapDto(1, 5, 9, "maps/map1.png");
        map = MapMapper.mapToEntity(mapDto);
    }

    @Test
    void testCreateMap() {
        ResponseEntity<Void> response = mapController.create(mapDto);

        verify(mapService).createMap(any(Map.class));
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testReadMapById_found() {
        when(mapService.readMapById(1)).thenReturn(Optional.of(map));

        ResponseEntity<MapDto> response = mapController.readMapById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mapDto.getLigne(), response.getBody().getLigne());
    }

    @Test
    void testReadMapById_notFound() {
        when(mapService.readMapById(1)).thenReturn(Optional.empty());

        ResponseEntity<MapDto> response = mapController.readMapById(1);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testReadAllMaps() {
        when(mapService.readAllMaps()).thenReturn(List.of(map));

        ResponseEntity<List<MapDto>> response = mapController.readAllMaps();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(mapDto.getChemin_image(), response.getBody().get(0).getChemin_image());
    }

    @Test
    void testUpdateMap() {
        ResponseEntity<Void> response = mapController.updateMap(1, mapDto);

        verify(mapService).updateMap(any(Map.class));
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteMap_mapFound() {
        when(mapService.readMapById(1)).thenReturn(Optional.of(map));

        ResponseEntity<Void> response = mapController.deleteMap(1);

        verify(zombieService).deleteZombiesByMapId(1);
        verify(mapService).deleteMapById(1);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteMap_mapNotFound() {
        when(mapService.readMapById(1)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = mapController.deleteMap(1);

        verify(zombieService, never()).deleteZombiesByMapId(anyInt());
        verify(mapService, never()).deleteMapById(anyInt());
        assertEquals(404, response.getStatusCodeValue());
    }
}
