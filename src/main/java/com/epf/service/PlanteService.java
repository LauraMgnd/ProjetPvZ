package com.epf.service;

import com.epf.model.Plante;
import java.util.List;
import java.util.Optional;

public interface PlanteService {
    void createPlante(Plante plante);
    Optional<Plante> readPlanteById(Integer idPlante);
    List<Plante> readAllPlantes();
    void updatePlante(Plante plante);
    void deletePlanteById(Integer idPlante);
}
