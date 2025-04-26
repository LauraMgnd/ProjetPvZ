package com.epf.mapper;

import com.epf.dto.ZombieDto;
import com.epf.model.Zombie;

public class ZombieMapper {

    public static ZombieDto zombieToDto(Zombie zombie) {
        return new ZombieDto(
                zombie.getIdZombie(),
                zombie.getNom(),
                zombie.getPointDeVie(),
                zombie.getAttaqueParSeconde(),
                zombie.getDegatAttaque(),
                zombie.getVitesseDeDeplacement(),
                zombie.getCheminImage(),
                zombie.getIdMap()
        );
    }

    public static Zombie zombieToEntity(ZombieDto dto) {
        return new Zombie(
                dto.getId_zombie(),
                dto.getNom(),
                dto.getPoint_de_vie(),
                dto.getAttaque_par_seconde(),
                dto.getDegat_attaque(),
                dto.getVitesse_de_deplacement(),
                dto.getChemin_image(),
                dto.getId_map()
        );
    }
}