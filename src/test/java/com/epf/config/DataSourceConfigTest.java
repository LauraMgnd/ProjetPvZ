package com.epf.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceConfigTest {

    @Test
    void testReadDB() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class)) {

            DataSource dataSource = context.getBean(DataSource.class);
            assertNotNull(dataSource, "Le DataSource ne doit pas être null");

            try (Connection connection = dataSource.getConnection()) {
                assertFalse(connection.isClosed(), "La connexion devrait être ouverte");

                Statement statement = connection.createStatement();

                // Plantes
                ResultSet resultSetPlante = statement.executeQuery("SELECT * FROM Plante");
                System.out.println("Plantes:");
                int planteCount = 0;
                while (resultSetPlante.next()) {
                    planteCount++;
                    int idPlante = resultSetPlante.getInt("id_plante");
                    String nom = resultSetPlante.getString("nom");
                    int pointDeVie = resultSetPlante.getInt("point_de_vie");
                    double attaqueParSeconde = resultSetPlante.getDouble("attaque_par_seconde");
                    int degatAttaque = resultSetPlante.getInt("degat_attaque");
                    int cout = resultSetPlante.getInt("cout");
                    double soleilParSeconde = resultSetPlante.getDouble("soleil_par_seconde");
                    String effet = resultSetPlante.getString("effet");
                    String cheminImage = resultSetPlante.getString("chemin_image");

                    System.out.println("ID Plante: " + idPlante);
                    System.out.println("Nom: " + nom);
                    System.out.println("Point de vie: " + pointDeVie);
                    System.out.println("Attaque par seconde: " + attaqueParSeconde);
                    System.out.println("Dégât d'attaque: " + degatAttaque);
                    System.out.println("Coût: " + cout);
                    System.out.println("Soleil par seconde: " + soleilParSeconde);
                    System.out.println("Effet: " + effet);
                    System.out.println("Chemin de l'image: " + cheminImage);
                    System.out.println("-----------------------------------");
                }
                assertTrue(planteCount >= 0, "La table Plante devrait être accessible");

                // Zombies
                ResultSet resultSetZombie = statement.executeQuery("SELECT * FROM Zombie");
                System.out.println("\nZombies:");
                int zombieCount = 0;
                while (resultSetZombie.next()) {
                    zombieCount++;
                    int idZombie = resultSetZombie.getInt("id_zombie");
                    String nomZombie = resultSetZombie.getString("nom");
                    int pointDeVieZombie = resultSetZombie.getInt("point_de_vie");
                    double attaqueParSecondeZombie = resultSetZombie.getDouble("attaque_par_seconde");
                    int degatAttaqueZombie = resultSetZombie.getInt("degat_attaque");
                    double vitesseDeDeplacement = resultSetZombie.getDouble("vitesse_de_deplacement");
                    String cheminImageZombie = resultSetZombie.getString("chemin_image");
                    int idMapZombie = resultSetZombie.getInt("id_map");

                    System.out.println("ID Zombie: " + idZombie);
                    System.out.println("Nom: " + nomZombie);
                    System.out.println("Point de vie: " + pointDeVieZombie);
                    System.out.println("Attaque par seconde: " + attaqueParSecondeZombie);
                    System.out.println("Dégât d'attaque: " + degatAttaqueZombie);
                    System.out.println("Vitesse de déplacement: " + vitesseDeDeplacement);
                    System.out.println("Chemin de l'image: " + cheminImageZombie);
                    System.out.println("ID Map: " + idMapZombie);
                    System.out.println("-----------------------------------");
                }
                assertTrue(zombieCount >= 0, "La table Zombie devrait être accessible");

                // Maps
                ResultSet resultSetMap = statement.executeQuery("SELECT * FROM Map");
                System.out.println("\nMaps:");
                int mapCount = 0;
                while (resultSetMap.next()) {
                    mapCount++;
                    int idMap = resultSetMap.getInt("id_map");
                    int ligne = resultSetMap.getInt("ligne");
                    int colonne = resultSetMap.getInt("colonne");
                    String cheminImageMap = resultSetMap.getString("chemin_image");

                    System.out.println("ID Map: " + idMap);
                    System.out.println("Ligne: " + ligne);
                    System.out.println("Colonne: " + colonne);
                    System.out.println("Chemin de l'image: " + cheminImageMap);
                    System.out.println("-----------------------------------");
                }
                assertTrue(mapCount >= 0, "La table Map devrait être accessible");

            } catch (SQLException e) {
                fail("Erreur SQL : " + e.getMessage());
            }

        }
    }
}
