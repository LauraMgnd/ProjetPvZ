package com.epf.controller;

import com.epf.dto.ZombieDto;
import com.epf.mapper.ZombieMapper;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
import com.epf.exception.EntityNotFoundException;

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
@RequestMapping("/zombies")
public class ZombieController {

    private static final Logger logger = LoggerFactory.getLogger(ZombieController.class);

    private final ZombieService zombieService;

    @Autowired
    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ZombieDto zombieDto) {
        logger.info("Demande de création d'un zombie: {}", zombieDto);
        Zombie zombie = ZombieMapper.zombieToEntity(zombieDto);
        try {
            zombieService.createZombie(zombie);
            logger.info("Zombie créé avec succès: {}", zombie);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error("Erreur lors de la création du zombie: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZombieDto> readZombieById(@PathVariable Integer id) {
        logger.info("Demande de lecture d'un zombie avec ID: {}", id);
        Optional<Zombie> zombie = zombieService.readZombieById(id);
        return zombie.map(z -> {
            logger.info("Zombie trouvé: {}", z);
            return ResponseEntity.ok(ZombieMapper.zombieToDto(z));
        }).orElseThrow(() -> {
            logger.error("Zombie non trouvé avec ID: {}", id);
            return new EntityNotFoundException("Zombie", id);
        });
    }

    @GetMapping
    public ResponseEntity<List<ZombieDto>> readAllZombies() {
        logger.info("Demande de lecture de tous les zombies");
        List<ZombieDto> zombieDtos = zombieService.readAllZombies().stream()
                .map(ZombieMapper::zombieToDto)
                .toList();
        logger.info("Nombre total de zombies trouvés: {}", zombieDtos.size());
        return ResponseEntity.ok(zombieDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateZombie(@PathVariable("id") Integer id,
                                             @Valid @RequestBody ZombieDto zombieDto) {
        logger.info("Demande de mise à jour du zombie avec ID: {}", id);
        zombieDto.setId_zombie(id);
        Zombie updatedZombie = ZombieMapper.zombieToEntity(zombieDto);
        try {
            zombieService.updateZombie(updatedZombie);
            logger.info("Zombie mis à jour avec succès: {}", updatedZombie);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du zombie avec ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZombie(@PathVariable("id") Integer id) {
        logger.info("Demande de suppression du zombie avec ID: {}", id);
        try {
            zombieService.deleteZombieById(id);
            logger.info("Zombie supprimé avec succès avec ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du zombie avec ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/map/{mapId}")
    public ResponseEntity<List<ZombieDto>> readZombiesByMapId(@PathVariable Integer mapId) {
        logger.info("Demande de lecture des zombies pour la carte avec ID: {}", mapId);
        List<ZombieDto> zombieDtos = zombieService.readZombiesByMapId(mapId).stream()
                .map(ZombieMapper::zombieToDto)
                .toList();
        logger.info("Nombre de zombies trouvés pour la carte {}: {}", mapId, zombieDtos.size());
        return ResponseEntity.ok(zombieDtos);
    }
}
