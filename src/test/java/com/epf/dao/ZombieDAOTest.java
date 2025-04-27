package com.epf.dao;

import com.epf.model.Zombie;
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

class ZombieDAOTest {

    private SingleConnectionDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private MapDAO mapDAO;
    private ZombieDAO zombieDAO;

    @BeforeEach
    void setUp(TestInfo testInfo) throws SQLException {
        String dbName = "testdb_" + testInfo.getDisplayName();
        dataSource = new SingleConnectionDataSource("jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1", "sa", "", true);
        jdbcTemplate = new JdbcTemplate(dataSource);
        mapDAO = new MapDAOImpl(jdbcTemplate);
        zombieDAO = new ZombieDAOImpl(jdbcTemplate, (MapDAOImpl) mapDAO);

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
        Zombie zombie = new Zombie("Zombie Test", 120, 0.9, 12, 0.6, "images/zombie/test.png", 2);
        zombieDAO.create(zombie);
        List<Zombie> zombies = zombieDAO.readAll();
        assertEquals(6, zombies.size());
    }

    @Test
    void testReadById() {
        Optional<Zombie> zombie = zombieDAO.readById(1);
        assertTrue(zombie.isPresent());
        assertEquals("Zombie de base", zombie.get().getNom());
    }

    @Test
    void testReadAll() {
        List<Zombie> zombies = zombieDAO.readAll();
        assertEquals(5, zombies.size());
    }

    @Test
    void testUpdate() {
        Optional<Zombie> original = zombieDAO.readById(1);
        assertTrue(original.isPresent());

        Zombie updated = new Zombie(1, "Zombie Buff", 300, 1.2, 20, 0.7, "images/zombie/buff.png", 2);
        zombieDAO.update(updated);

        Optional<Zombie> afterUpdate = zombieDAO.readById(1);
        assertTrue(afterUpdate.isPresent());
        assertEquals("Zombie Buff", afterUpdate.get().getNom());
        assertEquals(300, afterUpdate.get().getPointDeVie());
    }

    @Test
    void testDeleteById() {
        zombieDAO.deleteById(1);
        Optional<Zombie> zombie = zombieDAO.readById(1);
        assertFalse(zombie.isPresent());
    }

    @Test
    void testReadByMapId() {
        List<Zombie> zombies = zombieDAO.readByMapId(1);
        assertEquals(3, zombies.size()); // 3 zombies dans values.sql avec id_map = 1
    }

    @Test
    void testDeleteByMapId() {
        zombieDAO.deleteByMapId(1);
        List<Zombie> zombies = zombieDAO.readByMapId(1);
        assertTrue(zombies.isEmpty());
    }

    @Test
    void testCreateFailsIfMapNotExists() {
        Zombie zombie = new Zombie("Ghost Zombie", 50, 0.5, 5, 0.3, "images/zombie/ghost.png", 999);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            zombieDAO.create(zombie);
        });
        assertTrue(exception.getMessage().contains("n'existe pas"));
    }
}
