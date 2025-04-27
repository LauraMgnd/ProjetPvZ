package com.epf.service;

import com.epf.dao.PlanteDAO;
import com.epf.model.Plante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanteServiceImpl implements PlanteService {

    private final PlanteDAO planteDAO;

    @Autowired
    public PlanteServiceImpl(PlanteDAO planteDAO) {
        this.planteDAO = planteDAO;
    }

    @Override
    public void createPlante(Plante plante) {
        planteDAO.create(plante);
    }

    @Override
    public Optional<Plante> readPlanteById(Integer idPlante) {
        return planteDAO.readById(idPlante);
    }

    @Override
    public List<Plante> readAllPlantes() {
        return planteDAO.readAll();
    }

    @Override
    public void updatePlante(Plante plante) {
        planteDAO.update(plante);
    }

    @Override
    public void deletePlanteById(Integer idPlante) {
        planteDAO.deleteById(idPlante);
    }
}
