package com.epf.dao;

import com.epf.model.Map;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MapDAOTest {

    private SingleConnectionDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private MapDAO mapDAO;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = new SingleConnectionDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "", true);
        jdbcTemplate = new JdbcTemplate(dataSource);
        mapDAO = new MapDAOImpl(jdbcTemplate);

        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new org.springframework.core.io.ClassPathResource("init.sql"));
            ScriptUtils.executeSqlScript(conn, new org.springframework.core.io.ClassPathResource("values.sql"));
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().execute("DROP ALL OBJECTS");
        }
        dataSource.destroy();
    }


    @Test
    void testCreate() {
        Map newMap = new Map(7, 8, "images/map/new_map.png");
        mapDAO.create(newMap);
        List<Map> maps = mapDAO.readAll();
        assertEquals(4, maps.size());
    }

    @Test
    void testReadById() {
        Optional<Map> map = mapDAO.readById(1);
        assertTrue(map.isPresent());
        assertEquals(5, map.get().getLigne());
        assertEquals(9, map.get().getColonne());
    }

    @Test
    void testReadAll() {
        List<Map> maps = mapDAO.readAll();
        assertEquals(3, maps.size());
    }

    @Test
    void testUpdate() {
        Optional<Map> original = mapDAO.readById(1);
        assertTrue(original.isPresent());
        Map updated = new Map(1, 10, 10, "images/map/updated.png");
        mapDAO.update(updated);
        Optional<Map> afterUpdate = mapDAO.readById(1);
        assertEquals("images/map/updated.png", afterUpdate.get().getCheminImage());
        assertEquals(10, afterUpdate.get().getLigne());
    }

    @Test
    void testDeleteById() {
        jdbcTemplate.update("DELETE FROM Zombie WHERE id_map = 1"); // Pour éviter les contraintes de clé étrangère
        mapDAO.deleteById(1);
        Optional<Map> map = mapDAO.readById(1);
        assertFalse(map.isPresent());
    }
}
