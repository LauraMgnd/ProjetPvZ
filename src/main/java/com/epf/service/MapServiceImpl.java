package com.epf.service;

import com.epf.dao.MapDAO;
import com.epf.model.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapServiceImpl implements MapService {

    private final MapDAO mapDAO;

    @Autowired
    public MapServiceImpl(MapDAO mapDAO) {
        this.mapDAO = mapDAO;
    }

    @Override
    public void createMap(Map map) {
        mapDAO.create(map);
    }

    @Override
    public Optional<Map> readMapById(Integer idMap) {
        return mapDAO.readById(idMap);
    }

    @Override
    public List<Map> readAllMaps() {
        return mapDAO.readAll();
    }

    @Override
    public void updateMap(Map map) {
        mapDAO.update(map);
    }

    @Override
    public void deleteMapById(Integer idMap) {
        mapDAO.deleteById(idMap);
    }
}
