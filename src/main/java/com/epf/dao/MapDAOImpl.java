package com.epf.dao;

import com.epf.model.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MapDAOImpl implements MapDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MapDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper pour convertir les lignes SQL en objets Map
    private static final RowMapper<Map> mapRowMapper = (rs, rowNum) -> new Map(
            rs.getInt("id_map"),
            rs.getInt("ligne"),
            rs.getInt("colonne"),
            rs.getString("chemin_image")
    );

    @Override
    public void create(Map map) {
        String sql = """
                INSERT INTO map 
                (ligne, colonne, chemin_image)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                map.getLigne(),
                map.getColonne(),
                map.getCheminImage());
    }

    @Override
    public Optional<Map> readById(Integer idMap) {
        String sql = "SELECT * FROM map WHERE id_map = ?";
        return jdbcTemplate.query(sql, mapRowMapper, idMap).stream().findFirst();
    }

    @Override
    public List<Map> readAll() {
        String sql = "SELECT * FROM map";
        return jdbcTemplate.query(sql, mapRowMapper);
    }

    @Override
    public void update(Map newMapData) {
        String sql = """
                UPDATE map SET 
                ligne = ?, 
                colonne = ?, 
                chemin_image = ? 
                WHERE id_map = ?
                """;
        jdbcTemplate.update(sql,
                newMapData.getLigne(),
                newMapData.getColonne(),
                newMapData.getCheminImage(),
                newMapData.getIdMap());
    }

    @Override
    public void deleteById(Integer idMap) {
        String sql = "DELETE FROM map WHERE id_map = ?";
        jdbcTemplate.update(sql, idMap);
    }
}
