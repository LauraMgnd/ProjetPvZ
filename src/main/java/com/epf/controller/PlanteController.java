package com.epf.controller;

import com.epf.dto.PlanteDto;
import com.epf.mapper.PlanteMapper;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import com.epf.exception.EntityNotFoundException;

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

    private final PlanteService planteService;

    @Autowired
    public PlanteController(PlanteService planteService) {
        this.planteService = planteService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PlanteDto planteDto) {
        Plante plante = PlanteMapper.planteToEntity(planteDto);
        planteService.createPlante(plante);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanteDto> readPlanteById(@PathVariable Integer id) {
        Optional<Plante> plante = planteService.readPlanteById(id);
        return plante.map(p -> ResponseEntity.ok(PlanteMapper.planteToDto(p)))
                .orElseThrow(() -> new EntityNotFoundException("Plante", id));
    }

    @GetMapping
    public ResponseEntity<List<PlanteDto>> readAllPlantes() {
        List<PlanteDto> planteDtos = planteService.readAllPlantes().stream()
                .map(PlanteMapper::planteToDto)
                .toList();
        return ResponseEntity.ok(planteDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlante(@PathVariable("id") Integer id, @Valid @RequestBody PlanteDto planteDto) {
        planteDto.setId_plante(id);
        Plante updatedPlante = PlanteMapper.planteToEntity(planteDto);
        planteService.updatePlante(updatedPlante);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlante(@PathVariable("id") Integer id) {
        planteService.deletePlanteById(id);
        return ResponseEntity.noContent().build();
    }
}
