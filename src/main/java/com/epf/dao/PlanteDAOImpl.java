package com.epf.dao;

import com.epf.model.Plante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlanteDAOImpl implements PlanteDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlanteDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper pour convertir les lignes SQL en objets Plante
    private static final RowMapper<Plante> planteRowMapper = (rs, rowNum) -> new Plante(
            rs.getInt("id_plante"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getDouble("attaque_par_seconde"),
            rs.getInt("degat_attaque"),
            rs.getInt("cout"),
            rs.getDouble("soleil_par_seconde"),
            rs.getString("effet"),
            rs.getString("chemin_image")
    );

    @Override
    public void create(Plante plante) {
        String sql = """
                INSERT INTO plante 
                (nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                plante.getNom(),
                plante.getPointDeVie(),
                plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(),
                plante.getCout(),
                plante.getSoleilParSeconde(),
                plante.getEffet(),
                plante.getCheminImage());
    }

    @Override
    public Optional<Plante> readById(Integer idPlante) {
        String sql = "SELECT * FROM plante WHERE id_plante = ?";
        return jdbcTemplate.query(sql, planteRowMapper, idPlante).stream().findFirst();
    }

    @Override
    public List<Plante> readAll() {
        String sql = "SELECT * FROM plante";
        return jdbcTemplate.query(sql, planteRowMapper);
    }

    @Override
    public void update(Plante newPlanteData) {
        String sql = """
                UPDATE plante SET 
                nom = ?, 
                point_de_vie = ?, 
                attaque_par_seconde = ?, 
                degat_attaque = ?, 
                cout = ?, 
                soleil_par_seconde = ?, 
                effet = ?, 
                chemin_image = ?
                WHERE id_plante = ?
                """;
        jdbcTemplate.update(sql,
                newPlanteData.getNom(),
                newPlanteData.getPointDeVie(),
                newPlanteData.getAttaqueParSeconde(),
                newPlanteData.getDegatAttaque(),
                newPlanteData.getCout(),
                newPlanteData.getSoleilParSeconde(),
                newPlanteData.getEffet(),
                newPlanteData.getCheminImage(),
                newPlanteData.getIdPlante());
    }

    @Override
    public void deleteById(Integer idPlante) {
        String sql = "DELETE FROM plante WHERE id_plante = ?";
        jdbcTemplate.update(sql, idPlante);
    }
}
