package com.epf.dao;

import com.epf.model.Zombie;

import java.util.List;

public interface ZombieDAO extends GenericDAO<Zombie, Integer> {
    List<Zombie> readByMapId(int idMap);

    void deleteByMapId(int idMap);
}
