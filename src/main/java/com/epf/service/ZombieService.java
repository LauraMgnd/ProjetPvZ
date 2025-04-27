package com.epf.service;

import com.epf.model.Zombie;
import java.util.List;
import java.util.Optional;

public interface ZombieService {
    void createZombie(Zombie zombie);
    Optional<Zombie> readZombieById(Integer idZombie);
    List<Zombie> readAllZombies();
    void updateZombie(Zombie zombie);
    void deleteZombieById(Integer idZombie);
    List<Zombie> readZombiesByMapId(int idMap);

    void deleteZombiesByMapId(int idMap);
}
