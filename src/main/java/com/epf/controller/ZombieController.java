package com.epf.controller;

import com.epf.dto.ZombieDto;
import com.epf.mapper.ZombieMapper;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
import com.epf.exception.EntityNotFoundException;

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

    private final ZombieService zombieService;

    @Autowired
    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ZombieDto zombieDto) {
        Zombie zombie = ZombieMapper.zombieToEntity(zombieDto);
        zombieService.createZombie(zombie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZombieDto> readZombieById(@PathVariable Integer id) {
        Optional<Zombie> zombie = zombieService.readZombieById(id);
        return zombie.map(z -> ResponseEntity.ok(ZombieMapper.zombieToDto(z)))
                .orElseThrow(() -> new EntityNotFoundException("Zombie", id));
    }

    @GetMapping
    public ResponseEntity<List<ZombieDto>> readAllZombies() {
        List<ZombieDto> zombieDtos = zombieService.readAllZombies().stream()
                .map(ZombieMapper::zombieToDto)
                .toList();
        return ResponseEntity.ok(zombieDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateZombie(@PathVariable("id") Integer id, @Valid @RequestBody ZombieDto zombieDto) {
        zombieDto.setId_zombie(id);
        Zombie updatedZombie = ZombieMapper.zombieToEntity(zombieDto);
        zombieService.updateZombie(updatedZombie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZombie(@PathVariable("id") Integer id) {
        zombieService.deleteZombieById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/map/{mapId}")
    public ResponseEntity<List<ZombieDto>> readZombiesByMapId(@PathVariable Integer mapId) {
        List<ZombieDto> zombieDtos = zombieService.readZombiesByMapId(mapId).stream()
                .map(ZombieMapper::zombieToDto)
                .toList();
        return ResponseEntity.ok(zombieDtos);
    }
}
