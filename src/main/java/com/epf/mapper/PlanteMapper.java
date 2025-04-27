package com.epf.mapper;

import com.epf.dto.PlanteDto;
import com.epf.model.Plante;

public class PlanteMapper {

    public static PlanteDto planteToDto(Plante plante) {
        return new PlanteDto(
                plante.getIdPlante(),
                plante.getNom(),
                plante.getPointDeVie(),
                plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(),
                plante.getCout(),
                plante.getSoleilParSeconde(),
                plante.getEffet(),
                plante.getCheminImage()
        );
    }

    public static Plante planteToEntity(PlanteDto dto) {
        return new Plante(
                dto.getId_plante(),
                dto.getNom(),
                dto.getPoint_de_vie(),
                dto.getAttaque_par_seconde(),
                dto.getDegat_attaque(),
                dto.getCout(),
                dto.getSoleil_par_seconde(),
                dto.getEffet(),
                dto.getChemin_image()
        );
    }
}
