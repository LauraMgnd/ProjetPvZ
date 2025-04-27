package com.epf.service;

import com.epf.dao.ZombieDAO;
import com.epf.model.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZombieServiceTest {

    private ZombieDAO zombieDAO;
    private ZombieService zombieService;

    @BeforeEach
    void setUp() {
        zombieDAO = mock(ZombieDAO.class);
        zombieService = new ZombieServiceImpl(zombieDAO);
    }

    @Test
    void testCreateZombie() {
        Zombie zombie = new Zombie("Zombie Test", 100, 1.0, 10, 0.5, "path.png", 1);
        zombieService.createZombie(zombie);
        verify(zombieDAO).create(zombie);
    }

    @Test
    void testReadZombieById() {
        Zombie zombie = new Zombie(1, "Zombie Test", 100, 1.0, 10, 0.5, "path.png", 1);
        when(zombieDAO.readById(1)).thenReturn(Optional.of(zombie));

        Optional<Zombie> result = zombieService.readZombieById(1);

        assertTrue(result.isPresent());
        assertEquals("Zombie Test", result.get().getNom());
        verify(zombieDAO).readById(1);
    }

    @Test
    void testReadAllZombies() {
        when(zombieDAO.readAll()).thenReturn(List.of(
                new Zombie(1, "Zombie Test 1", 100, 1.0, 10, 0.5, "z1.png", 1),
                new Zombie(2, "Zombie Test2", 150, 0.8, 12, 0.4, "z2.png", 1)
        ));

        List<Zombie> result = zombieService.readAllZombies();

        assertEquals(2, result.size());
        verify(zombieDAO).readAll();
    }

    @Test
    void testUpdateZombie() {
        Zombie zombie = new Zombie(1, "Zombie Update", 200, 1.5, 20, 0.6, "update.png", 2);
        zombieService.updateZombie(zombie);
        verify(zombieDAO).update(zombie);
    }

    @Test
    void testDeleteZombieById() {
        zombieService.deleteZombieById(1);
        verify(zombieDAO).deleteById(1);
    }

    @Test
    void testReadZombiesByMapId() {
        when(zombieDAO.readByMapId(2)).thenReturn(List.of());
        zombieService.readZombiesByMapId(2);
        verify(zombieDAO).readByMapId(2);
    }

    @Test
    void testDeleteZombiesByMapId() {
        zombieService.deleteZombiesByMapId(3);
        verify(zombieDAO).deleteByMapId(3);
    }
}
