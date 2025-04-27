package com.epf.service;

import com.epf.dao.MapDAO;
import com.epf.model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapServiceTest {

    private MapDAO mapDAO;
    private MapService mapService;

    @BeforeEach
    void setUp() {
        mapDAO = mock(MapDAO.class);
        mapService = new MapServiceImpl(mapDAO);
    }

    @Test
    void testCreateMap() {
        Map map = new Map(1, 10, 10, "image/path.png");
        mapService.createMap(map);
        verify(mapDAO).create(map);
    }

    @Test
    void testReadMapById() {
        Map map = new Map(1, 10, 10, "image/path.png");
        when(mapDAO.readById(1)).thenReturn(Optional.of(map));

        Optional<Map> result = mapService.readMapById(1);

        assertTrue(result.isPresent());
        assertEquals(10, result.get().getLigne());
        assertEquals("image/path.png", result.get().getCheminImage());
        verify(mapDAO).readById(1);
    }

    @Test
    void testReadAllMaps() {
        when(mapDAO.readAll()).thenReturn(List.of(
                new Map(1, 10, 10, "image1/path.png"),
                new Map(2, 15, 20, "image2/path.png")
        ));

        List<Map> result = mapService.readAllMaps();

        assertEquals(2, result.size());
        verify(mapDAO).readAll();
    }

    @Test
    void testUpdateMap() {
        Map map = new Map(1, 10, 10, "image/update.png");
        mapService.updateMap(map);
        verify(mapDAO).update(map);
    }

    @Test
    void testDeleteMapById() {
        mapService.deleteMapById(1);
        verify(mapDAO).deleteById(1);
    }
}
