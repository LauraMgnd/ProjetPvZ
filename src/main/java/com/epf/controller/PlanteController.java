package com.epf.controller;

import com.epf.dto.PlanteDto;
import com.epf.mapper.PlanteMapper;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import com.epf.exception.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plantes")
public class PlanteController {

    private static final Logger logger = LoggerFactory.getLogger(PlanteController.class);

    private final PlanteService planteService;

    @Autowired
    public PlanteController(PlanteService planteService) {
        this.planteService = planteService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PlanteDto planteDto) {
        logger.info("Demande de création d'une plante: {}", planteDto);
        Plante plante = PlanteMapper.planteToEntity(planteDto);
        try {
            planteService.createPlante(plante);
            logger.info("Plante créée avec succès: {}", plante);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la plante: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanteDto> readPlanteById(@PathVariable Integer id) {
        logger.info("Demande de lecture de la plante avec ID: {}", id);
        Optional<Plante> plante = planteService.readPlanteById(id);
        return plante.map(p -> {
            logger.info("Plante trouvée: {}", p);
            return ResponseEntity.ok(PlanteMapper.planteToDto(p));
        }).orElseThrow(() -> {
            logger.error("Plante non trouvée avec ID: {}", id);
            return new EntityNotFoundException("Plante", id);
        });
    }

    @GetMapping
    public ResponseEntity<List<PlanteDto>> readAllPlantes() {
        logger.info("Demande de lecture de toutes les plantes");
        List<PlanteDto> planteDtos = planteService.readAllPlantes().stream()
                .map(PlanteMapper::planteToDto)
                .toList();
        logger.info("Nombre total de plantes trouvées: {}", planteDtos.size());
        return ResponseEntity.ok(planteDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlante(@PathVariable("id") Integer id, @Valid @RequestBody PlanteDto planteDto) {
        logger.info("Demande de mise à jour de la plante avec ID: {}", id);
        planteDto.setId_plante(id);
        Plante updatedPlante = PlanteMapper.planteToEntity(planteDto);
        try {
            planteService.updatePlante(updatedPlante);
            logger.info("Plante mise à jour avec succès: {}", updatedPlante);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la plante avec ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlante(@PathVariable("id") Integer id) {
        logger.info("Demande de suppression de la plante avec ID: {}", id);
        try {
            planteService.deletePlanteById(id);
            logger.info("Plante supprimée avec succès avec ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la plante avec ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
