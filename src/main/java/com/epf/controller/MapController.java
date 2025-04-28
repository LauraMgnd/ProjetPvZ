package com.epf.controller;

import com.epf.dto.MapDto;
import com.epf.mapper.MapMapper;
import com.epf.model.Map;
import com.epf.service.MapService;
import com.epf.exception.EntityNotFoundException;

import com.epf.service.ZombieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService mapService;
    private final ZombieService zombieService;

    @Autowired
    public MapController(MapService mapService, ZombieService zombieService) {
        this.mapService = mapService;
        this.zombieService = zombieService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody MapDto mapDto) {
        Map map = MapMapper.mapToEntity(mapDto);
        mapService.createMap(map);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MapDto> readMapById(@PathVariable Integer id) {
        Optional<Map> map = mapService.readMapById(id);
        return map.map(m -> ResponseEntity.ok(MapMapper.mapToDto(m)))
                .orElseThrow(() -> new EntityNotFoundException("Map", id));
    }

    @GetMapping
    public ResponseEntity<List<MapDto>> readAllMaps() {
        List<MapDto> mapDtos = mapService.readAllMaps().stream()
                .map(MapMapper::mapToDto)
                .toList();
        return ResponseEntity.ok(mapDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMap(@PathVariable("id") Integer id, @Valid @RequestBody MapDto mapDto) {
        mapDto.setId_map(id);
        Map updatedMap = MapMapper.mapToEntity(mapDto);
        mapService.updateMap(updatedMap);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable("id") Integer id) {
        Optional<Map> map = mapService.readMapById(id);
        if (map.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Suppression des zombies associés à la carte
        zombieService.deleteZombiesByMapId(id);

        mapService.deleteMapById(id);
        return ResponseEntity.noContent().build();
    }
}
