package com.epf.dao;

import com.epf.model.Plante;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlanteDAOTest {

    private SingleConnectionDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlanteDAO planteDAO;

    @BeforeEach
    void setUp(TestInfo testInfo) throws SQLException {
        String dbName = "testdb_" + testInfo.getDisplayName();
        dataSource = new SingleConnectionDataSource("jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1", "sa", "", true);
        jdbcTemplate = new JdbcTemplate(dataSource);
        planteDAO = new PlanteDAOImpl(jdbcTemplate);

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
        Plante plante = new Plante("Cactus", 120, 1.2, 15, 75, 0.0, "normal", "images/plante/cactus.png");
        planteDAO.create(plante);
        List<Plante> plantes = planteDAO.readAll();
        assertEquals(6, plantes.size());
    }

    @Test
    void testReadById() {
        Optional<Plante> plante = planteDAO.readById(1);
        assertTrue(plante.isPresent());
        assertEquals("Tournesol", plante.get().getNom());
        assertEquals(25.0, plante.get().getSoleilParSeconde());
    }

    @Test
    void testReadAll() {
        List<Plante> plantes = planteDAO.readAll();
        assertEquals(5, plantes.size());
    }


    @Test
    void testUpdate() {
        Optional<Plante> original = planteDAO.readById(2); // Pois Tireur
        assertTrue(original.isPresent());

        Plante updated = new Plante(2, "Mega Pois", 180, 2.0, 30, 150, 0.0, "normal", "images/plante/megapois.png");
        planteDAO.update(updated);

        Optional<Plante> afterUpdate = planteDAO.readById(2);
        assertTrue(afterUpdate.isPresent());
        assertEquals("Mega Pois", afterUpdate.get().getNom());
        assertEquals(180, afterUpdate.get().getPointDeVie());
    }

    @Test
    void testDeleteById() {
        planteDAO.deleteById(5); // Noix
        Optional<Plante> plante = planteDAO.readById(5);
        assertFalse(plante.isPresent());
    }
}
