package com.epf.controller;

import com.epf.dto.ZombieDto;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
import com.epf.mapper.ZombieMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZombieControllerTest {

    @InjectMocks
    private ZombieController zombieController;

    @Mock
    private ZombieService zombieService;

    private ZombieDto zombieDto;
    private Zombie zombie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        zombieDto = new ZombieDto(1, "Zombie Test", 100, 1.5, 10, 2.0, "chemin/image.png", 1);
        zombie = ZombieMapper.zombieToEntity(zombieDto);
    }

    @Test
    void testCreateZombie() {
        ResponseEntity<Void> response = zombieController.create(zombieDto);

        verify(zombieService).createZombie(any(Zombie.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testReadZombieById() {
        when(zombieService.readZombieById(1)).thenReturn(Optional.of(zombie));

        ResponseEntity<ZombieDto> response = zombieController.readZombieById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(zombieDto.getNom(), response.getBody().getNom());
    }

    @Test
    void testReadAllZombies() {
        when(zombieService.readAllZombies()).thenReturn(List.of(zombie));

        ResponseEntity<List<ZombieDto>> response = zombieController.readAllZombies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(zombieDto.getNom(), response.getBody().get(0).getNom());
    }

    @Test
    void testUpdateZombie() {
        ResponseEntity<Void> response = zombieController.updateZombie(1, zombieDto);

        verify(zombieService).updateZombie(any(Zombie.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteZombie() {
        ResponseEntity<Void> response = zombieController.deleteZombie(1);

        verify(zombieService).deleteZombieById(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testReadZombiesByMapId() {
        when(zombieService.readZombiesByMapId(1)).thenReturn(List.of(zombie));

        ResponseEntity<List<ZombieDto>> response = zombieController.readZombiesByMapId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
