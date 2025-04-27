package com.epf.mapper;

import com.epf.dto.ZombieDto;
import com.epf.model.Zombie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZombieMapperTest {

    @Test
    void testZombieToDto() {
        Zombie zombie = new Zombie(1, "Zombie Classique", 150, 1.0, 20, 1.2, "zombie.png", 3);

        ZombieDto dto = ZombieMapper.zombieToDto(zombie);

        assertEquals(zombie.getIdZombie(), dto.getId_zombie());
        assertEquals(zombie.getNom(), dto.getNom());
        assertEquals(zombie.getPointDeVie(), dto.getPoint_de_vie());
        assertEquals(zombie.getAttaqueParSeconde(), dto.getAttaque_par_seconde());
        assertEquals(zombie.getDegatAttaque(), dto.getDegat_attaque());
        assertEquals(zombie.getVitesseDeDeplacement(), dto.getVitesse_de_deplacement());
        assertEquals(zombie.getCheminImage(), dto.getChemin_image());
        assertEquals(zombie.getIdMap(), dto.getId_map());
    }

    @Test
    void testZombieToEntity() {
        ZombieDto dto = new ZombieDto(2, "Zombie Rapide", 80, 2.5, 10, 3.0, "zombie_rapide.png", 2);

        Zombie zombie = ZombieMapper.zombieToEntity(dto);

        assertEquals(dto.getId_zombie(), zombie.getIdZombie());
        assertEquals(dto.getNom(), zombie.getNom());
        assertEquals(dto.getPoint_de_vie(), zombie.getPointDeVie());
        assertEquals(dto.getAttaque_par_seconde(), zombie.getAttaqueParSeconde());
        assertEquals(dto.getDegat_attaque(), zombie.getDegatAttaque());
        assertEquals(dto.getVitesse_de_deplacement(), zombie.getVitesseDeDeplacement());
        assertEquals(dto.getChemin_image(), zombie.getCheminImage());
        assertEquals(dto.getId_map(), zombie.getIdMap());
    }
}
