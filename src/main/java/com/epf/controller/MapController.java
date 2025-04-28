package com.epf.controller;

import com.epf.dto.MapDto;
import com.epf.mapper.MapMapper;
import com.epf.model.Map;
import com.epf.service.MapService;
import com.epf.exception.EntityNotFoundException;

import com.epf.service.ZombieService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);

    private final MapService mapService;
    private final ZombieService zombieService;

    @Autowired
    public MapController(MapService mapService, ZombieService zombieService) {
        this.mapService = mapService;
        this.zombieService = zombieService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody MapDto mapDto) {
        logger.info("Demande de création d'une carte: {}", mapDto);
        Map map = MapMapper.mapToEntity(mapDto);
        try {
            mapService.createMap(map);
            logger.info("Carte créée avec succès: {}", map);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la carte: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MapDto> readMapById(@PathVariable Integer id) {
        logger.info("Demande de lecture de la carte avec ID: {}", id);
        Optional<Map> map = mapService.readMapById(id);
        return map.map(m -> {
            logger.info("Carte trouvée: {}", m);
            return ResponseEntity.ok(MapMapper.mapToDto(m));
        }).orElseThrow(() -> {
            logger.error("Carte non trouvée avec ID: {}", id);
            return new EntityNotFoundException("Map", id);
        });
    }

    @GetMapping
    public ResponseEntity<List<MapDto>> readAllMaps() {
        logger.info("Demande de lecture de toutes les cartes");
        List<MapDto> mapDtos = mapService.readAllMaps().stream()
                .map(MapMapper::mapToDto)
                .toList();
        logger.info("Nombre total de cartes trouvées: {}", mapDtos.size());
        return ResponseEntity.ok(mapDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMap(@PathVariable("id") Integer id, @Valid @RequestBody MapDto mapDto) {
        logger.info("Demande de mise à jour de la carte avec ID: {}", id);
        mapDto.setId_map(id);
        Map updatedMap = MapMapper.mapToEntity(mapDto);
        try {
            mapService.updateMap(updatedMap);
            logger.info("Carte mise à jour avec succès: {}", updatedMap);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la carte avec ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable("id") Integer id) {
        logger.info("Demande de suppression de la carte avec ID: {}", id);
        Optional<Map> map = mapService.readMapById(id);
        if (map.isEmpty()) {
            logger.error("Carte non trouvée avec ID: {}", id);
            throw new EntityNotFoundException("Map", id);
        }

        // Suppression des zombies associés à la carte
        try {
            zombieService.deleteZombiesByMapId(id);
            logger.info("Zombies associés à la carte avec ID {} supprimés", id);
            mapService.deleteMapById(id);
            logger.info("Carte supprimée avec succès avec ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la carte avec ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
