package com.epf.dao;

import com.epf.model.Map;
import com.epf.model.Zombie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ZombieDAOImpl implements ZombieDAO {

    private final JdbcTemplate jdbcTemplate;
    private final MapDAOImpl mapDao;

    @Autowired
    public ZombieDAOImpl(JdbcTemplate jdbcTemplate, MapDAOImpl mapDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapDao = mapDao;
    }

    // RowMapper pour convertir les lignes SQL en objets Zombie
    private static final RowMapper<Zombie> zombieRowMapper = (rs, rowNum) -> new Zombie(
            rs.getInt("id_zombie"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getDouble("attaque_par_seconde"),
            rs.getInt("degat_attaque"),
            rs.getDouble("vitesse_de_deplacement"),
            rs.getString("chemin_image"),
            rs.getInt("id_map")
    );

    @Override
    public void create(Zombie zombie) {
        // vérifier que la map existe avant d'insérer le zombie
        Optional<Map> mapExistante = mapDao.readById(zombie.getIdMap());

        if (mapExistante.isEmpty()) {
            throw new IllegalArgumentException("La carte avec l'id " + zombie.getIdMap() + " n'existe pas.");
        }

        String sql = """
                INSERT INTO zombie 
                (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                zombie.getNom(),
                zombie.getPointDeVie(),
                zombie.getAttaqueParSeconde(),
                zombie.getDegatAttaque(),
                zombie.getVitesseDeDeplacement(),
                zombie.getCheminImage(),
                zombie.getIdMap());
    }

    @Override
    public Optional<Zombie> readById(Integer idZombie) {
        String sql = "SELECT * FROM zombie WHERE id_zombie = ?";
        return jdbcTemplate.query(sql, zombieRowMapper, idZombie).stream().findFirst();
    }

    @Override
    public List<Zombie> readAll() {
        String sql = "SELECT * FROM zombie";
        return jdbcTemplate.query(sql, zombieRowMapper);
    }

    @Override
    public void update(Zombie newZombieData) {
        // vérifier que la map existe avant de modifier le zombie
        Optional<Map> mapExistante = mapDao.readById(newZombieData.getIdMap());

        if (mapExistante.isEmpty()) {
            throw new IllegalArgumentException("La carte avec l'id " + newZombieData.getIdMap() + " n'existe pas.");
        }

        String sql = """
                UPDATE zombie SET 
                nom = ?, 
                point_de_vie = ?, 
                attaque_par_seconde = ?, 
                degat_attaque = ?, 
                vitesse_de_deplacement = ?, 
                chemin_image = ?, 
                id_map = ?
                WHERE id_zombie = ?
                """;
        jdbcTemplate.update(sql,
                newZombieData.getNom(),
                newZombieData.getPointDeVie(),
                newZombieData.getAttaqueParSeconde(),
                newZombieData.getDegatAttaque(),
                newZombieData.getVitesseDeDeplacement(),
                newZombieData.getCheminImage(),
                newZombieData.getIdMap(),
                newZombieData.getIdZombie());
    }

    @Override
    public void deleteById(Integer idZombie) {
        String sql = "DELETE FROM zombie WHERE id_zombie = ?";
        jdbcTemplate.update(sql, idZombie);
    }

    @Override
    public List<Zombie> readByMapId(int idMap) {
        String sql = "SELECT * FROM zombie WHERE id_map = ?";
        return jdbcTemplate.query(sql, zombieRowMapper, idMap);
    }

    @Override
    public void deleteByMapId(int idMap) {
        String sql = "DELETE FROM zombie WHERE id_map = ?";
        jdbcTemplate.update(sql, idMap);
    }
}
