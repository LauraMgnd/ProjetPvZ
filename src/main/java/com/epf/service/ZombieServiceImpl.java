package com.epf.service;

import com.epf.dao.ZombieDAO;
import com.epf.model.Zombie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZombieServiceImpl implements ZombieService {

    private final ZombieDAO zombieDAO;

    @Autowired
    public ZombieServiceImpl(ZombieDAO zombieDAO) {
        this.zombieDAO = zombieDAO;
    }

    @Override
    public void createZombie(Zombie zombie) {
        zombieDAO.create(zombie);
    }

    @Override
    public Optional<Zombie> readZombieById(Integer idZombie) {
        return zombieDAO.readById(idZombie);
    }

    @Override
    public List<Zombie> readAllZombies() {
        return zombieDAO.readAll();
    }

    @Override
    public void updateZombie(Zombie zombie) {
        zombieDAO.update(zombie);
    }

    @Override
    public void deleteZombieById(Integer idZombie) {
        zombieDAO.deleteById(idZombie);
    }

    @Override
    public List<Zombie> readZombiesByMapId(int idMap) {
        return zombieDAO.readByMapId(idMap);
    }
}
