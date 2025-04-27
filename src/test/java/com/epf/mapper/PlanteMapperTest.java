package com.epf.mapper;

import com.epf.dto.PlanteDto;
import com.epf.model.Plante;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanteMapperTest {

    @Test
    void testPlanteToDto() {
        Plante plante = new Plante(1, "Tournesol", 100, 0.0, 0, 50, 25.0, "produit du soleil", "image/tournesol.png");

        PlanteDto dto = PlanteMapper.planteToDto(plante);

        assertEquals(plante.getIdPlante(), dto.getId_plante());
        assertEquals(plante.getNom(), dto.getNom());
        assertEquals(plante.getPointDeVie(), dto.getPoint_de_vie());
        assertEquals(plante.getAttaqueParSeconde(), dto.getAttaque_par_seconde());
        assertEquals(plante.getDegatAttaque(), dto.getDegat_attaque());
        assertEquals(plante.getCout(), dto.getCout());
        assertEquals(plante.getSoleilParSeconde(), dto.getSoleil_par_seconde());
        assertEquals(plante.getEffet(), dto.getEffet());
        assertEquals(plante.getCheminImage(), dto.getChemin_image());
    }

    @Test
    void testPlanteToEntity() {
        PlanteDto dto = new PlanteDto(2, "Pisto-pois", 80, 1.5, 20, 100, 0.0, "tire des pois", "image/pisto-pois.png");

        Plante plante = PlanteMapper.planteToEntity(dto);

        assertEquals(dto.getId_plante(), plante.getIdPlante());
        assertEquals(dto.getNom(), plante.getNom());
        assertEquals(dto.getPoint_de_vie(), plante.getPointDeVie());
        assertEquals(dto.getAttaque_par_seconde(), plante.getAttaqueParSeconde());
        assertEquals(dto.getDegat_attaque(), plante.getDegatAttaque());
        assertEquals(dto.getCout(), plante.getCout());
        assertEquals(dto.getSoleil_par_seconde(), plante.getSoleilParSeconde());
        assertEquals(dto.getEffet(), plante.getEffet());
        assertEquals(dto.getChemin_image(), plante.getCheminImage());
    }
}
