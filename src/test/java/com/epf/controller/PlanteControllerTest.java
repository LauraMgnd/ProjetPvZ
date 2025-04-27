package com.epf.controller;

import com.epf.dto.PlanteDto;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import com.epf.mapper.PlanteMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanteControllerTest {

    @InjectMocks
    private PlanteController planteController;

    @Mock
    private PlanteService planteService;

    private PlanteDto planteDto;
    private Plante plante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        planteDto = new PlanteDto(1, "Tournesol", 100, 0.0, 0, 50, 25.0, "produit du soleil", "image/tournesol.png");
        plante = PlanteMapper.planteToEntity(planteDto);
    }

    @Test
    void testCreatePlante() {
        ResponseEntity<Void> response = planteController.create(planteDto);

        verify(planteService).createPlante(any(Plante.class));
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testReadPlanteById_found() {
        when(planteService.readPlanteById(1)).thenReturn(Optional.of(plante));

        ResponseEntity<PlanteDto> response = planteController.readPlanteById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(planteDto.getNom(), response.getBody().getNom());
    }

    @Test
    void testReadPlanteById_notFound() {
        when(planteService.readPlanteById(1)).thenReturn(Optional.empty());

        ResponseEntity<PlanteDto> response = planteController.readPlanteById(1);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testReadAllPlantes() {
        when(planteService.readAllPlantes()).thenReturn(List.of(plante));

        ResponseEntity<List<PlanteDto>> response = planteController.readAllPlantes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(planteDto.getNom(), response.getBody().get(0).getNom());
    }

    @Test
    void testUpdatePlante() {
        ResponseEntity<Void> response = planteController.updatePlante(1, planteDto);

        verify(planteService).updatePlante(any(Plante.class));
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeletePlante() {
        ResponseEntity<Void> response = planteController.deletePlante(1);

        verify(planteService).deletePlanteById(1);
        assertEquals(204, response.getStatusCodeValue());
    }
}
