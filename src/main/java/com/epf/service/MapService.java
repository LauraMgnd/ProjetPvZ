package com.epf.service;

import com.epf.model.Map;
import java.util.List;
import java.util.Optional;

public interface MapService {
    void createMap(Map map);
    Optional<Map> readMapById(Integer idMap);
    List<Map> readAllMaps();
    void updateMap(Map map);
    void deleteMapById(Integer idMap);
}
