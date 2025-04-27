package com.epf.service;

import com.epf.dao.PlanteDAO;
import com.epf.model.Plante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanteServiceTest {

    private PlanteDAO planteDAO;
    private PlanteService planteService;

    @BeforeEach
    void setUp() {
        planteDAO = mock(PlanteDAO.class);
        planteService = new PlanteServiceImpl(planteDAO);
    }

    @Test
    void testCreatePlante() {
        Plante plante = new Plante(1, "Plante1", 100, 2.5, 30, 50, 1.0, "Effet1", "path/to/image");
        planteService.createPlante(plante);
        verify(planteDAO).create(plante);
    }

    @Test
    void testReadPlanteById() {
        Plante plante = new Plante(1, "Plante1", 100, 2.5, 30, 50, 1.0, "Effet1", "path/to/image");
        when(planteDAO.readById(1)).thenReturn(Optional.of(plante));

        Optional<Plante> result = planteService.readPlanteById(1);

        assertTrue(result.isPresent());
        assertEquals("Plante1", result.get().getNom());
        assertEquals(100, result.get().getPointDeVie());
        verify(planteDAO).readById(1);
    }

    @Test
    void testReadAllPlantes() {
        when(planteDAO.readAll()).thenReturn(List.of(
                new Plante(1, "Plante1", 100, 2.5, 30, 50, 1.0, "Effet1", "path/to/image1"),
                new Plante(2, "Plante2", 120, 3.0, 40, 60, 1.5, "Effet2", "path/to/image2")
        ));

        List<Plante> result = planteService.readAllPlantes();

        assertEquals(2, result.size());
        assertEquals("Plante1", result.get(0).getNom());
        verify(planteDAO).readAll();
    }

    @Test
    void testUpdatePlante() {
        Plante plante = new Plante(1, "PlanteUpdated", 150, 3.5, 50, 70, 2.0, "EffetUpdated", "path/to/updated/image");
        planteService.updatePlante(plante);
        verify(planteDAO).update(plante);
    }

    @Test
    void testDeletePlanteById() {
        planteService.deletePlanteById(1);
        verify(planteDAO).deleteById(1);
    }
}
